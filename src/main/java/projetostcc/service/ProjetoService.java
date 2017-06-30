package projetostcc.service;

import projetostcc.model.Projeto;

import java.util.List;

/**
 * Created by DU on 14/04/2017.
 */
public interface ProjetoService {

    /**
     * Método que retorna uma lista de projetos levando em consideração
     * os parâmetros de ordenação, filtros e limitação do resultado
     *
     * @param sortFields String que define qual campo deve ser utilizado para a ordenação
     * @param sortOrders Boolean que define qual deve ser a forma de ordenação (ascendente ou descendente)
     * @param filterFields Uma lista com os campos de filtragem
     * @param filterValues Uma lista com os valores de filtragem
     * @param from Indice do primeiro registro do resultado a ser retornado
     * @param to Indice do ultimo registro do resultado a ser retornado
     * @return uma lista de projetos
     */
    List<Projeto> findAll(List<String> sortFields, List<String> sortOrders, List<String> filterFields, List<String> filterValues, int from, int to);

    /**
     * Método que retorna o total de projetos que satisfazem alguns parâmetros filtragem
     *
     * @param filterFields Uma lista com os campos de filtragem
     * @param filterValues Uma lista com os valores de filtragem
     * @return números de projetos que satisfazem os parametros
     */
    Long total(List<String> filterFields, List<String> filterValues);
}
