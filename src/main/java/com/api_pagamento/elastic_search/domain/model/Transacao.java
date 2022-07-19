package com.api_pagamento.elastic_search.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@Data = @Data é uma anotação que gera o código padronizado para classes Java: getters para todos os campos,
//setters para todos os campos não-finais e o toString apropriado, equals e implementações hashCode
//que envolvem os campos da classe.

@Data

//@Builder = Builder é um padrão de projeto de software criacional que permite a separação da construção de
//um objeto complexo da sua representação, de forma que o mesmo processo de construção possa criar diferentes representações.

@Builder

//@NoArgsConstructor = essa anotação é responsável por gerar um construtor sem parâmetros,
//vale ressaltar que se tiver campos final na sua classe deverá usar um atributo force = true em sua anotação.

//Obs: A especificação do JPA diz: "A especificação JPA requer que todas as classes persistentes tenham um construtor no-arg.
//Este construtor pode ser público ou protegido. Como o compilador cria automaticamente um construtor no-arg padrão
//quando nenhum outro construtor é definido, apenas as classes que definem os construtores também deve incluir um construtor sem argumentos."

@NoArgsConstructor

//@AllArgsConstructor = essa anotação é responsável por gerar um construtor com um parâmetro para cada atributo de sua classe.

@AllArgsConstructor

//@Document: essa anotação marca uma classe como sendo um objeto de domínio que queremos persistir no banco de dados
@Document(indexName = "transacao")
public class Transacao  {

    //https://stackoverflow.com/questions/45431574/not-auto-generating-id-for-long-type-but-for-string-type-field-in-spring-data-el

    //https://www.elastic.co/guide/en/elasticsearch/reference/current/keyword.html
    //FieldType:KEYWORD: É usado para conteúdo estruturado, como IDs, endereços de e-mail, nomes de host, códigos de
    //                   status, códigos postais ou tags.
    //                   Os campos de palavra-chave geralmente são usados na classificação , agregações e consultas
    //                   em nível de termo, como term.
    //                   Ao contrário dos campos de texto, os campos de palavra-chav não são analisados, ou seja,
    //                   não funcionam em buscas de texto completo (fulltext search)
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @NotBlank
    @Field(type = FieldType.Text)
    private String cartao;

    //https://stackoverflow.com/questions/23403149/elasticsearch-relationship-mappings-one-to-one-and-one-to-many
    //https://www.elastic.co/blog/managing-relations-inside-elasticsearch

    //Inner Object

    //Fácil, rápido, eficiente
    //Aplicável apenas quando relacionamentos um para um são mantidos
    //Não há necessidade de consultas especiais

    //Ex:
    //
    //{
    //  "name" : "Zach",
    //  "car" : [
    //    {
    //      "make" : "Saturn",
    //      "model" : "SL"
    //    },
    //    {
    //      "make" : "Subaru",
    //      "model" : "Imprezza"
    //    }
    //  ]
    //}
    //{
    //  "name" : "Bob",
    //  "car" : [
    //    {
    //      "make" : "Saturn",
    //      "model" : "Imprezza"
    //    }
    //  ]
    //}
    //
    //query: car.make=Saturn AND car.model=Imprezza
    //R: Os dois documentos serão retornados, pois:
    //{
    //  "name" : "Zach",
    //  "car.make" : ["Saturn", "Subaru"]
    //  "car.model" : ["SL", "Imprezza"]
    //}

    //Nested

    //Os documentos aninhados são armazenados no mesmo bloco do Lucene, o que ajuda no desempenho de leitura/consulta.
    //Ler um documento aninhado é mais rápido que o pai/filho equivalente.
    //A atualização de um único campo em um documento aninhado (pai ou filho aninhado) força o ES a reindexar todo o
    //documento aninhado. Isso pode ser muito caro para documentos aninhados grandes
    //Documentos aninhados de “referência cruzada” são impossíveis
    //Mais adequado para dados que não mudam com frequência

    //Ex: {
    //  "name" : "Zach",
    //  "car" : [
    //    {
    //      "make" : "Saturn",
    //      "model" : "SL"
    //    },
    //    {
    //      "make" : "Subaru",
    //      "model" : "Imprezza"
    //    }
    //  ]
    //}
    //{
    //  "name" : "Bob",
    //  "car" : [
    //    {
    //      "make" : "Saturn",
    //      "model" : "Imprezza"
    //    }
    //  ]
    //}
    //{
    //  "person":{
    //    "properties":{
    //      "name" : {
    //        "type" : "string"
    //      },
    //      "car":{
    //        "type" : "nested"
    //      }
    //    }
    //  }
    //}
    //query: car.make=Saturn AND car.model=Imprezza
    //R:
    //{
    //  "name" : "Bob",
    //  "car" : [
    //    {
    //      "make" : "Saturn",
    //      "model" : "Imprezza"
    //    }
    //  ]
    //}

    @Valid
    @NotNull
    @Field(type = FieldType.Object)
    private Descricao descricao;

    @Valid
    @NotNull
    @Field(type = FieldType.Object)
    private FormaPagamento formaPagamento;

}