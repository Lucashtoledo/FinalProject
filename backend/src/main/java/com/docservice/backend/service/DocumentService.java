package com.docservice.backend.service;

import com.docservice.backend.DTO.DocumentDTO;
import com.docservice.backend.entity.Client;
import com.docservice.backend.entity.Document;
import com.docservice.backend.entity.Form;
import com.docservice.backend.entity.NecessaryDoc;
import com.docservice.backend.repository.ClientRepository;
import com.docservice.backend.repository.FormRepository;
import com.docservice.backend.repository.DocumentRepository;
import com.docservice.backend.repository.NecessaryDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FormService formService;
    @Autowired
    private NecessaryDocRepository necessaryDocRepository;

    public Document uploadDocument(Long formId, Long clientId, MultipartFile file) throws IOException {
        Form form = formRepository.findById(formId).orElseThrow(() -> new RuntimeException("Form not found"));
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));

        // Criando o objeto Document
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setContent(file.getBytes());  // Armazenando o conteúdo do arquivo no banco

        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long documentId) {
        return documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public void deleteDocument(Long documentId) {
        documentRepository.deleteById(documentId);
    }

    // Método para obter todos os documentos de processo
    public ResponseEntity<List<DocumentDTO>> getDocByProcessId(Long id) {
        System.out.println("----------------------------INICIO DO METODO-------------------------------------");

        List<Document> documents = documentRepository.findAll();
        List<DocumentDTO> documentDTOS = new ArrayList<>();

        List<NecessaryDoc> necessaryDocs = necessaryDocRepository.findAll();
        List<NecessaryDoc> listDoc = new ArrayList<>();
        String formId = String.valueOf(formService.getProcessById(id));


        // Laço para obter os necessaryDoc com id do formulario
        for (NecessaryDoc necessaryDoc1 : necessaryDocs) {
            String necessaryDocId = String.valueOf(necessaryDoc1.getForm().getId());
            if(necessaryDocId.equals(formId)) {
                listDoc.add(necessaryDoc1);
            }
        }
        for (NecessaryDoc necessaryDoc2 : listDoc) {
            System.out.println("--------------------------------NECESSARY DOC: " + necessaryDoc2.getDocumentName());
        }

        // Laço para preencher a lista DTO
        for (NecessaryDoc doc: listDoc){ // Itera sobre
            for (Document document: documents){
                if(document.getId().equals(doc.getId())){
                    documentDTOS.add(toDTO(document));
                    System.out.println("--------------------------------------------- ID DOC: "+ doc.getId());
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(documentDTOS);
    }

    public DocumentDTO toDTO(Document document) {
        return new DocumentDTO(
          document.getContent(),
          document.getId(),
          document.getNecessaryDoc().getId(),
          document.getFileName()
        );
    }

   /* public ResponseEntity<InputStreamResource> downloadFile(Long id, Long startPosition) throws IOException {
        // Aqui você busca o documento no banco de dados
        File file[] = getDocumentFileById(id);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        long fileLength = file.length();
        long start = (startPosition != null) ? startPosition : 0;
        long end = Math.min(start + CHUNK_SIZE - 1, fileLength - 1);
        long rangeLength = end - start + 1;

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/pdf");
        responseHeaders.add("Content-Length", String.valueOf(rangeLength));
        responseHeaders.add("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file) {
            {
                skip(start);
            }
        });

        return new ResponseEntity<>(resource, responseHeaders, HttpStatus.PARTIAL_CONTENT);
    }
*/
    public ResponseEntity<byte[]> getDocumentFileById(Long id) {
        documentRepository.findById(id);
        byte[] fileContent = documentRepository.findById(id).get().getContent();
        return ResponseEntity.status(HttpStatus.OK).body(fileContent);
    }

    public ResponseEntity<byte[]> uploadFile(Long id, byte[] file) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        document.setContent(file);
        documentRepository.save(document);
        return ResponseEntity.status(HttpStatus.OK).body(document.getContent());
    }

    public byte[] getDocumentPart(Long docId, int partIndex) {
        Document document = documentRepository.findById(docId).orElseThrow(() -> new RuntimeException("Document not found"));
        byte[] content = document.getContent();
        int partSize = 4 * 1024 * 1024; // 4MB

        // Calcular o número máximo de partes
        int totalParts = (int) Math.ceil((double) content.length / partSize);

        // Verificar se o partIndex está dentro do limite
        if (partIndex >= totalParts) {
            throw new IllegalArgumentException("Part index is beyond the document length");
        }

        int start = partIndex * partSize;
        int end = Math.min(start + partSize, content.length);

        return Arrays.copyOfRange(content, start, end);
    }


}
