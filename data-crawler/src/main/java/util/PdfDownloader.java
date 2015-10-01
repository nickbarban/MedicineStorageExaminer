package util;

import domain.DataCrawlerException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PdfDownloader {
    public static FileInputStream downloadAndGetAsStream(String url) throws DataCrawlerException {
        try {
            URL link = new URL(url);
            String tempDirPath = FileUtils.getTempDirectoryPath();
            String pdfDir = tempDirPath + "medicalPdf";
            FileUtils.forceMkdir(new File(pdfDir));
            String pdfName = FilenameUtils.getBaseName(url) + "." + FilenameUtils.getExtension(url);
            String pathToPdfFile = pdfDir + File.separator + pdfName;
            File pdfFile = new File(pathToPdfFile);
            FileUtils.copyURLToFile(link, pdfFile);
            return FileUtils.openInputStream(pdfFile);
        } catch (MalformedURLException e) {
            throw new DataCrawlerException("Wrong url: " + url, e);
        } catch (IOException e) {
            throw new DataCrawlerException("Some IO error", e);
        }
    }
}
