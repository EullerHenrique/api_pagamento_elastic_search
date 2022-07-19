package com.api_pagamento.elastic_search.repository;

import com.api_pagamento.elastic_search.domain.model.Transacao;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransacaoRepository extends ElasticsearchRepository<Transacao, String> {

    //"{\"query_string\": {\"query\": \"?0\"}}": Realiza uma busca full text, ou seja, realiza a busca de um valor
    //sem especefiar em que campo ele foi armazenado. Essa busca não precisa ser exata. ou seja, pode-se buscar apenas
    //uma parte do valor armazenado.
    //Ex: Valor armazenado: Teste de busca
    //    Valor buscado: busca
    //https://stackoverflow.com/questions/34147471/elasticsearch-how-to-search-for-a-value-in-any-field-across-all-types-in-one
    @Query("{\"query_string\": {\"query\": \"?0\"}}")
    List<Transacao> findBy(String value);

}


