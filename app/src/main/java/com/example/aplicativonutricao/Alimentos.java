package com.example.aplicativonutricao;

import java.util.ArrayList;

public class Alimentos {

    private String[] legumes;
    private String[] quantidadeLegumes;
    private String[] frutas;
    private String[] quantidadeFrutas;
    private String[] cereais;
    private String[] quantidadeCereais;
    private String[] carnes;
    private String[] quantidadeCarnes;
    private String[] leite;
    private String[] quantidadeLeite;
    private String[] graos;
    private String[] quantidadeGraos;

    private String[] get;


    public Alimentos(){



        legumes = new String[]{"Abóbora cozida",
                "Abobrinha cozida",
                "Batata inglesa cozida",
                "Berinjela ensopada",
                "Beterraba cozida/crua",
                "Brócolis cozido",
                "Cenoura cozida/ crua",
                "Chuchu cozido",
                "Couve flor",
                "Espinafre cozido",
                "Maxixe refolgado",
                "Quiabo refolgado",
                "Repolho refolgado",
                "Vagem cozida",
                "Pepino",
                "Tomate"};
        quantidadeLegumes = new String[]{
                "150",
                "60",
                "30",
                "50",
                "70",
                "100",
                "75",
                "85",
                "75",
                "100",
                "75",
                "40",
                "50",
                "100",
                "50",
                "50"};
        frutas = new String[]{
                "Abacaxi",
                "Abacate",
                "Acerola",
                "Banana maça/prata",
                "Cajá",
                "Caju",
                "Caqui",
                "Carambola",
                "Goiaba",
                "Jaca",
                "Jabuticaba",
                "Kiwi",
                "Laranja",
                "Maçã",
                "Mamão papaia/formosa",
                "Manga",
                "Maracujá",
                "Melão",
                "Melancia",
                "Morango",
                "Pêra",
                "Pinha",
                "Salada de frutas",
                "Uva"
        };
        quantidadeFrutas = new String[]{
                "100",
                "35",
                "144",
                "65",
                "50",
                "140",
                "80",
                "150",
                "100",
                "72",
                "90",
                "85",
                "140",
                "130",
                "160",
                "85",
                "45",
                "115",
                "250",
                "120",
                "110",
                "80",
                "75",
                "100"
        };
        cereais = new String[]{
                "Arroz branco",
                "Arroz integral",
                "Banana comprida",
                "Batata doce",
                "Biscoito salgado",
                "Biscoito doce",
                "Bolo simples qualquer sabor",
                "Cuscuz de milho",
                "Inhame",
                "Macaxeira cozida",
                "Macarrão",
                "Pão francês",
                "Pão de forma",
                "Purê batata, macaxeira, inhame, abóbora",
                "Tapioca com coco",
                "Torrada",
                "Aveia",
                "Granola"
        };
        quantidadeCereais = new  String[]{
                "100",
                "160",
                "110",
                "150",
                "30",
                "35",
                "50",
                "70",
                "150",
                "125",
                "200",
                "50",
                "50",
                "50",
                "50",
                "40",
                "36",
                "35"
        };

        carnes = new String[]{
                "Bacalhau cozido",
                "Bife grelhado",
                "Bife cozido",
                "Bife role",
                "Carne moída",
                "Carneiro assado ou guisado",
                "Charque magra",
                "Fígado cozido ou frito",
                "File de frango grelhado",
                "Frango cozido",
                "Filé de meluza",
                "Soja",
                "Ovo de galinha",
                "Ovo de codorna"

        };

        quantidadeCarnes = new String[]{
                "160",
                "120",
                "100",
                "75",
                "75",
                "70",
                "75",
                "100",
                "120",
                "150",
                "200",
                "75",
                "100",
                "100"

        };

        leite = new String[]{
                "Iogurte desnatado",
                "Iogurte com polpa de fruta",
                "Bebida láctea",
                "Coalhada desnatada",
                "Leite integral",
                "Leite desnatado",
                "Leite semidesnatado",
                "Leite de soja",
                "Leite em pó",
                "Queijo coalho",
                "Queijo de manteiga",
                "Queijo mozzarella",
                "Queijo prato",
                "Queijo ricota",
                "Requeijão",
                "Requeijão light"
        };
        quantidadeLeite = new String[]{
                "200",
                "120",
                "200",
                "140",
                "200",
                "200",
                "200",
                "300",
                "30",
                "75",
                "45",
                "45",
                "45",
                "70",
                "45",
                "90"

        };
        graos = new String[]{
                "Ervilha fresca",
                "Ervilha e/ ou milho enlatados",
                "Grão de bico",
                "Feijão preto/ mulatinho",
                "Feijão verde",
                "Lentilha"

        };

        quantidadeGraos = new String[]{
                "100",
                "100",
                "45",
                "150",
                "150",
                "95"

        };



    }

    public  int[] getLength(){
        int[] length = new int[]{legumes.length, frutas.length, cereais.length, carnes.length, leite.length, graos.length};
        return length;
    }

    public String[] getLegumes(int i){

        get = new String[]{legumes[i], quantidadeLegumes[i]};
        return get;
    }

    public String[] getFrutas(int i){

        get = new String[]{frutas[i], quantidadeFrutas[i]};
        return get;
    }

    public String[] getCereais(int i){

        get = new String[]{cereais[i], quantidadeCereais[i]};
        return get;
    }

    public String[] getCarnes(int i){

        get = new String[]{carnes[i], quantidadeCarnes[i]};
        return get;
    }

    public String[] getLeite(int i){

        get = new String[]{leite[i], quantidadeLeite[i]};
        return get;
    }

    public String[] getGraos(int i){

        get = new String[]{graos[i], quantidadeGraos[i]};
        return get;
    }

    public int getPeso(int grupo, int i){

        int peso = 0;
        if (grupo == 1){
            peso = Integer.parseInt(getLegumes(i)[1]);
        }
        if (grupo == 2){
            peso = Integer.parseInt(getFrutas(i)[1]);
        }
        if (grupo == 3){
            peso = Integer.parseInt(getCereais(i)[1]);
        }
        if (grupo == 4){
            peso = Integer.parseInt(getCarnes(i)[1]);
        }
        if (grupo == 5){
            peso = Integer.parseInt(getLeite(i)[1]);

        }
        if (grupo == 6){
            peso = Integer.parseInt(getGraos(i)[1]);
        }

        return peso;
    }

    public ArrayList<String> getLeiteMl(){
        int cont = 0;
        ArrayList<String> leiteMl = new ArrayList<>();

        while (cont < 9){
            leiteMl.add(leite[cont]);
            cont = cont + 1;
        }

        return leiteMl;
    }

}
