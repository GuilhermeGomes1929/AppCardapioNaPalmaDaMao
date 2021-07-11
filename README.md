# Aplicativo Cardápio na palma da mão

## Objetivo
Criar um aplicativo Android que substitua o cardápio impresso, tenha as mesmas funcionalidades e resolva alguns problemas. E, dessa forma, melhore a experiência do paciente, priorizando o seu conforto e desempenho.
<br>
Dentre esses problemas, podemos citar:

- Dificuldade em carregar;
- Perda ou esquecimento;
- Consulta ao cardápio difícil e demorada;
- Falta de versatilidade e conforto (nem sempre o ambiente é propício para ter várias folhas de papel na mão)

## Tecnologias usadas no App
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
<img src="https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white" />

## Funcionalidades

- Consultar o cardápio principal;
- Mostrar as porções em gramas e na forma de medidas caseira;
- Mostrar possíveis substituições de alimentos e suas respectivas porções;
- Cadastrar as medidas da composição corporal do paciente;
- Criar gráficos para a análise de desempenho;
- Registrar a quantidade de água bebida durante o dia e criar lembretes;
- Operar de forma offline;
- Utilizar o WhatsApp como recurso de compartilhamento.

## Telas 

- Meu cardápio;
- Meu corpo;
- Gráficos;
- Lembrete de água.

## Entidades:

### BodyModel
- data;
- weight;
- goal;
- bodyFat;
- shoulders;
- rightArm;
- leftArm;
- chest;
- waist;
- hip;
- rightLeg;
- leftLeg;
- rightCalf;
- leftCalf;
- hight.

### PersonModel

- name;
- age;
- sex;
- height.

### MenuModel

- id;
- foodGroup;
- food;
- portion;
- subs.

###FoodListModel

- id;
- foodGroup;
- foodName;
- weight;
- homeMeasure;
- calories;
- Double homePortions.



