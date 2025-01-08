package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        //Faz a leitura do arquivo CSV
        List<Produto> produtos = Files.lines(Paths.get("produtos.csv"))
                .skip(1)//Pula a linha de cabeçalho
                .map(line-> {
                    String[] fields = line.split(",");//define cada campo como sendo separado por uma virgula
                    return new Produto(fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2]));
                    //cria um novo produto utilizando os campos 0(nome), 1(preço) e 2(quantidade)
                    //utilizando o parse para ter certeza que o campo terá o tipo correto de dado
                })
                .collect(Collectors.toList());

        //Lista todos os produtos
        System.out.println("Lista de produtos importados");
        produtos.forEach(p-> System.out.println("Descrição: "+p.getNome()+ " , " +
                                                "Preço: "+p.getPreco() + " , " +
                                                "Quantidade: "+ p.getQuantidade()));
        System.out.println();

        //Filtra os produtos com preço maior que 20
        List<Produto> produtosCaros = produtos.stream()
                .filter(p-> p.getPreco() >20)
                .collect(Collectors.toList());
        System.out.println("Produtos com valor maior que 20");
        produtosCaros.forEach(p-> System.out.println(p.getNome() + " - " + p.getPreco()));

        System.out.println();

        //Agrupar por nome e soma a quantidade de cada produtos
        Map<String, Integer> quantidadePorProduto = produtos.stream()
                .collect(Collectors.groupingBy(
                        Produto::getNome,
                        Collectors.summingInt(Produto::getQuantidade)
                ));

        System.out.println("Quantidade total por produto:");
        quantidadePorProduto.forEach((nome, quantidade)-> System.out.println(nome + ": " + quantidade));
    }
}