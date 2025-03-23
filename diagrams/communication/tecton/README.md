## Tekton3 tesztesetek
~ Mert egy PR-ben változtak, és újra kell hangolni őket a teszt diagrammokkal.

|Teszteset|Diagram fájlnév|Használt objektumok|
|:--------|:--------------|:------------------|
|Tecton Grow Thread Success|[tecton3_1](./tecton3_1.puml)|mt1-ből nő új MushroomThread t2-re|
|Tecton Grow Body Success|[tecton3_1](./tecton3_1.puml)|t1-re nő egy MushroomBody|
|Tecton Grow Thread Fail|[tecton3_2](./tecton3_2.puml)|t3-ra nőne a fonál mt1-ből, de sikertelen, mert t1 (mt1) és t3 nem szomszédos|
|Tecton Grow Body Spore Fail|[tecton3_2](./tecton3_2.puml)|t1-re nőne gombatest, de sikertelen, mert nincs spóra rajta|
|Tecton Grow Body Thread Fail|[tecton3_3](./tecton3_3.puml)|t2-re nőne gombatest, de sikertelen, mert nincs fonál t2-n, de van elég spóra|
|Tecton Grow Body Body Fail|[tecton3_4](./tecton3_4.puml)|t1-re nőne gombatest, van rajta elég spóra és van ott fonál, de van ott már másik gombatest, ezért sikertelen|
