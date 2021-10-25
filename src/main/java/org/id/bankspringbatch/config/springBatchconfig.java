package org.id.bankspringbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.id.bankspringbatch.entities.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class springBatchconfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired
    private ItemWriter<BankTransaction> bankTransactionItemWriter;
    @Autowired
    private ItemProcessor<BankTransaction,BankTransaction> bankTransactionItemProcessor;


    /*@Autowired
    public springBatchconfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                             ItemReader<BankTransaction> bankTransactionItemReader, ItemWriter<BankTransaction> bankTransactionItemWriter,
                             ItemProcessor<BankTransaction, BankTransaction> bankTransactionItemProcessor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.bankTransactionItemReader = bankTransactionItemReader;
        this.bankTransactionItemWriter = bankTransactionItemWriter;
        this.bankTransactionItemProcessor = bankTransactionItemProcessor;
    }*/


    @Bean
    public Job BankJob(){
        Step step1 = stepBuilderFactory.get("step-load-data")
                .<BankTransaction,BankTransaction>chunk(4)
                .reader(bankTransactionItemReader)
                .processor(bankTransactionItemProcessor)
                .writer(bankTransactionItemWriter)
                .build();

        return jobBuilderFactory.get("job-load-job")
                .start(step1)
                .build();
    }

    @Bean
    public FlatFileItemReader<BankTransaction> fileItemReader (@Value("${inputFile}") Resource inputFile){

        FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("flateFile1");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLineMapper(lineMapper());

        return  flatFileItemReader;


    }

    @Bean
    public LineMapper<BankTransaction> lineMapper() {
        DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","accountId","strTransactionDate","transactionType","amount");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BankTransaction.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;




    }



}
