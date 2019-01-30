package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountResource;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.extensions.junit5.MicronautJunit5Extension;
import io.reactivex.Flowable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@MicronautTest
class AccountControllerTest {

  /*  private static EmbeddedServer server;
    @Inject
    @Client("/")
    private static HttpClient client;*/
/*  @Inject
  AccountClient client;
  @Inject
  EmbeddedServer embeddedServer;*/

/*    @Inject
    @Client("/")
    RxHttpClient client;
*/
@Inject
EmbeddedServer embeddedServer;

    //ccountClient client;
    HttpClient client ;
    @BeforeEach
    public void setUp(){
        client =  HttpClient.create(embeddedServer.getURL());

    }
/*    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeAll
    public static void setupServer() {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server .getApplicationContext() .createBean(HttpClient.class, server.getURL());
    }*/

    //  @BeforeAll
    //  public static void setupServer() {
    //   server = ApplicationContext.run(EmbeddedServer.class);
/*        client = server
                .getApplicationContext()
                .createBean(HttpClient.class, server.getURL());*/
    // }

    @Test
    void a(){
        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto.setAccountName("account1");
        createAccountDto.setOwnerName("john");

        CreateAccountDto createAccountDto2 = new CreateAccountDto();
        createAccountDto2.setAccountName("account2");
        createAccountDto2.setOwnerName("mark");

        client.toBlocking().exchange(HttpRequest.POST("/account", createAccountDto));
        client.toBlocking().exchange(HttpRequest.POST("/account", createAccountDto2));
        //  HttpResponse<List> retrieve = client.toBlocking().exchange(HttpRequest.GET("/account"), Argument.of(List.class,AccountResource.class));
        //  System.out.println("--" + retrieve);
        List<AccountResource> retriev2e = client.toBlocking().retrieve(HttpRequest.GET("/account"),Argument.of(List.class,AccountResource.class));
         assertThat(retriev2e).hasSize(2).allMatch(ar->ar.getAmount().equals(BigDecimal.ZERO));


         DepositDto depositDto = new DepositDto();
        depositDto.setAggregateId(retriev2e.get(0).getAggregateId());
        depositDto.setAmount("300");
         client.toBlocking().exchange(HttpRequest.POST("/account/deposit",depositDto));

        List<AccountResource> retriev2 = client.toBlocking().retrieve(HttpRequest.GET("/account"),Argument.of(List.class,AccountResource.class));
        assertThat(retriev2).hasSize(2).anyMatch(ar -> ar.getAggregateId().equals(retriev2e.get(0).getAggregateId()) && ar.getAmount().equals(new BigDecimal("300")));

        TransferDto transferDto = new TransferDto();
        transferDto.setAggregateIfFrom(retriev2e.get(0).getAggregateId());
        transferDto.setAggregateIdTo(retriev2e.get(1).getAggregateId());
        transferDto.setAmount("200");
        client.toBlocking().exchange(HttpRequest.POST("/account/transfer",transferDto));


        List<AccountResource> retriev3 = client.toBlocking().retrieve(HttpRequest.GET("/account"),Argument.of(List.class,AccountResource.class));
        assertThat(retriev3).hasSize(2).allMatch(ar->ar.getAmount().equals(new BigDecimal("200")));


        // List<AccountResource> all = client.findAll();
 /*       HttpResponse httpResponse = client.create(createAccountDto);
        System.out.println(httpResponse.getStatus());
        HttpResponse httpResponse1 = client.create(createAccountDto);
        System.out.println(httpResponse1.getStatus());
        List<AccountResource> all2 = client.findAll();
        List<AccountResource> all3 = client.findAll();*/
       // assertThat(all).hasSize(1);
    }

}