package com.api_pagamento.elastic_search.domain.model;

import com.api_pagamento.elastic_search.domain.enumeration.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

//@Data = @Data é uma anotação que gera o código padronizado para classes Java: getters para todos os campos,
//setters para todos os campos não-finais e o toString apropriado, equals e implementações hashCode
//que envolvem os campos da classe.

@Data

//@Builder = Builder é um padrão de projeto de software criacional que permite a separação da construção de
//um objeto complexo da sua representação, de forma que o mesmo processo de construção possa criar diferentes representações.

@Builder

//@NoArgsConstructor = essa anotação é responsável por gerar um construtor sem parâmetros,
//vale ressaltar que se tiver campos final na sua classe deverá usar um atributo force = true em sua anotação.

@NoArgsConstructor

//@AllArgsConstructor = essa anotação é responsável por gerar um construtor com um parâmetro para cada atributo de sua classe.

//Obs: A especificação do JPA diz: "A especificação JPA requer que todas as classes persistentes tenham um construtor no-arg.
//Este construtor pode ser público ou protegido. Como o compilador cria automaticamente um construtor no-arg padrão
//quando nenhum outro construtor é definido, apenas as classes que definem os construtores também deve incluir um construtor sem argumentos."

@AllArgsConstructor

public class Descricao {

    //@NotNull: https://www.baeldung.com/java-bean-validation-not-null-empty-blank
    @NotBlank
    @Field(type = FieldType.Text)
    private String valor;

    @NotBlank
    @Field(type = FieldType.Text)
    private String dataHora;

    @NotBlank
    @Field(type = FieldType.Text)
    private String estabelecimento;

    @Field(type = FieldType.Text)
    private String nsu;

    @Field(type = FieldType.Text)
    private String codigoAutorizacao;

    @Field(type = FieldType.Text)
    private StatusEnum status;

}