package org.afripay.afripay.utils;


import lombok.extern.slf4j.Slf4j;
import org.afripay.afripay.exceptions.GeneralException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ExportUtil {

    public static final String EXPORTS = "exports";
    public static final String UPLOADS = "uploads";


    private FileManagementUtil createDirectory(String name) {
        FileManagementUtil fileManagementUtil = new FileManagementUtil(name);
        fileManagementUtil.createDirectory();
        return fileManagementUtil;
    }

    public <K> List<K> convertExcelToDTO(MultipartFile multipartFile, Class<K> type) {
        log.info("converting excel to DTO for {}...", type.getName());

        // String fileName = multipartFile.getOriginalFilename();

        FileManagementUtil fileManagementUtil = createDirectory(UPLOADS);

        File file = fileManagementUtil.transferUploaded(multipartFile);
        if (Objects.isNull(file)) {
            throw new GeneralException("Error occurred while parsing document");
        }
        log.info("successfully copied file to disk...");

        List<K> excelDTO = new ArrayList<>();
//        try {
//            excelDTO = Poiji.fromExcel(file, type);
//            log.info("Retrieved excel => {} and docs => {}", fileName, excelDTO);
//        } catch (Exception e) {
//            throw new GeneralException("Error occurred while parsing excel");
//        }

        boolean result = file.delete();
        String message = result ? "successfully deleted file" : "file deletion failed";
        log.info(message);

        return excelDTO;
    }


    // private void deleteAllFilesInFolders(String... folderNames) {
    //     for (String folderName : folderNames) {
    //         FileManagementUtil fileManagementUtil = new FileManagementUtil(folderName);
    //         fileManagementUtil.deleteAllFileInFolder();
    //     }
    // }

}