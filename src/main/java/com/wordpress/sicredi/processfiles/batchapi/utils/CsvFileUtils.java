package com.wordpress.sicredi.processfiles.batchapi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.wordpress.sicredi.processfiles.batchapi.dto.AccountDataDto;
import com.wordpress.sicredi.processfiles.batchapi.model.AccountData;

public class CsvFileUtils {

    private static final String PATH_INPUT_FILE = "csv/fileInput/";
    private static final String PATH_OUTPUT_FILE = "src/main/resources/csv/fileOutput/";
    private CSVReader csvReader;
    private CSVWriter csvWriter;
    private String fileName;
    private File file;
    private FileReader fileReader;
    private FileWriter fileWriter;

    public CsvFileUtils(String fileName, boolean isInput) {

        if(isInput){
            this.fileName = PATH_INPUT_FILE + fileName + ".csv";
        }else {
        	this.fileName = PATH_OUTPUT_FILE + fileName + "-" + DateUtils.getNow().replace(":", "-") + "-.csv";
        }
    }

    public AccountDataDto read() throws IOException {

        if(this.csvReader == null){
            getFileToRead();
            getFileReader();
            getCSVReader();
        }

        String[] fields = this.csvReader.readNext();

        if(fields == null){
            return null;
        }

        List<String> list = Arrays.asList(fields);
        
        if(list.size() >= 4) {        	
            return new AccountDataDto(list.get(0), list.get(1), list.get(2), list.get(3));
        }

        return new AccountDataDto(list.toString());
    }

    private void getFileToRead() {

        ClassLoader classLoader = this.getClass().getClassLoader();

        if(this.file == null){
            String filePath = classLoader.getResource(this.fileName).getFile();
            this.file = new File(filePath);
        }
    }

    private void getFileReader() throws FileNotFoundException {
        if(this.fileReader == null){
            this.fileReader = new FileReader(this.file);
        }
    }

    private void getCSVReader() {
        if(this.csvReader == null){
            this.csvReader = new CSVReader(this.fileReader);
        }
    }

    public void closeReader() throws IOException {
        this.csvReader.close();
        this.fileReader.close();
    }

    public void writer(AccountData accountData) throws IOException {

        if(this.csvWriter == null){
            getFileToWrite();
            getFileWriter();
            getCSVWriter();
        }

        List<String> accountDataList = new ArrayList<>();
        
        if(accountData.getAgencia() != null){
        	accountDataList.add(accountData.getAgencia());
        }
        
        if(accountData.getConta() != null){
        	accountDataList.add(accountData.getConta());
        }
        
        if(accountData.getStatus() != null){
        	accountDataList.add(accountData.getStatus().toString());
        }              

        if(accountData.getSaldo() != null){
            accountDataList.add(accountData.getSaldo().toString());
        }

        if(accountData.getProcessado() != null){
        	accountDataList.add(accountData.getProcessado().toString());
        }

        this.csvWriter.writeNext(accountDataList.stream().toArray(String[]::new));
    }


    public void writerError(String[] accountDataError) throws IOException {

        if(this.csvWriter == null){
            getFileToWrite();
            getFileWriter();
            getCSVWriter();
        }

        this.csvWriter.writeNext(accountDataError);
    }

    private void getFileToWrite() throws IOException {
        if(this.file == null){
            this.file = new File(this.fileName);
            this.file.createNewFile();
        }
    }

    private void getFileWriter() throws IOException {
        if(this.fileWriter == null){
            this.fileWriter = new FileWriter(this.file, true);
        }
    }

    private void getCSVWriter() {
        if (this.csvWriter == null){
            this.csvWriter = new CSVWriter(this.fileWriter);
        }
    }

    public void closeWriter() throws IOException {
        this.csvWriter.close();
    }

}