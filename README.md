# Proiect POO etapa2
### Pitur Sebastian 324CA
## Clase
  ### Pachetul entities
  #### Interfata Entity
    Implementata de toate celelalte clase din pachet, cu metoda modify, care
    implementeaza schimbarile care au loc asupra entitatilor de la o luna la
    alta.
  #### Clasa Consumer
    Din care fac parte instantele de consumator care se folosesc in program.
  #### Clasa Distributor
    Din care fac parte instantele de distribuitor care se folosesc in program, 
    acestia implementeaza Observer.
  #### Clasa Producer
    Din care fac parte instantele de producator care se folosesc in program, 
    acestia extind Observable.
  ### Pachetul io
  #### Enumul Constants
    Contine anumite constante si metoda care transforma aceste constante in 
    stringuri, dar si metoda care transforma stringuri reprezentand tipuri
    de energie in Constante corespunzatoare.
  #### Clasa Input
    Instanta acestei clase contine listele cu producatori, consumatori si 
    distribuitori, dar si schimbarile care au loc luna de luna, in constructor,
    primind adresa fisierului de intrare din care preia datele pe care le 
    pune in listele de care am vorbit mai sus.
  #### Clasa Writer
    Instanta acestei clase care primeste la constructor fisierul de iesire,
    are metode care transforma datele din obiectele cu care lucram efectiv
    in program in obiecte json, pe care la final cu functia closeJSON le 
    scrie in fisierul de iesire, pe care il are salvat in camp.
  #### Clasa EntityFactory
    Clasa Singleton care creeaza instantele de Entity si Change.
  ### Pachetul entityatt
  #### Clasa Change 
    E extinsa de clasele ProducerChange si DistributorChange care reprezinta 
    schimbarile lunare care pot fi aplicate entitatilor, acestea au in comun
    faptul ca instantele lor au campuri cu id-ul entitatii, si o dimensiune,
    fie ca aceasta e energyPerDistributor sau infrastructureCost.
  #### Clasa EntityModifier
    Prin metoda modifyEntities, care apeleaza metodele modifyProducers, 
    modifyConsumers si modifyDistributors, aplica schimbarile din timpul lunii
    care au loc pentru consumatori si distribuitori adica plata consumatorilor
    catre distribuitori, cheltuielile distribuitorilor si schimbarile
    producatorilor de la sfarsitul lunii, adica schimbarea campului 
    energyperDistributor.
  #### Clasa Instancer
    Care are rolul de a instantia consumatori, distribuitori, producatori cu un
    anumit id, folosindu-se de listele din instanta de Input si de a adauga 
    noi consumatori si schimbarile de cost ale distribuitorilor.
  #### Clasa Mapper
    Are o metoda getMap care construieste un dictionar intre producatori si 
    costul contractului oferit de acestia in luna curenta.
  #### Clasa Pricer
    Contine metode care abordeaza diferite interactiuni intre consumatori si 
    distribuitori, toate cazurile acestora inclusiv cand exista restante. De 
    asemenea seteaza costurile distribuitorilor, inclusiv cel de productie
    care depinde de producatorii cu care are contract.
  ### Pachetul Contract
  #### Clasa Contract
    Este mostenita de clasele ContractConsumerDistributor si ContractDistributorProducer.
    Contine id-ul receiverului si al providerului.
  #### Clasa Contractor
    Care este implementata de alte 2 clase, ContractorConsumer care prin metoda 
    setContracts incheie contractele intre Consumatori si Distribuitori si 
    ContractorProducer, care incheie contractele dintre Distribuitori si Producatori.
  ### Pachetul Strategies
  #### Interfata Strategy
    Este implementata de clasele GreenStartegy, PriceStrategy si QuantityStrategy, care
    au metode diferite de a sorta o lista de producatori.
  #### Enumul EnergyChoiceStrategyType
    Enumera tipurile de strategii posibile pe care le pot avea producatorii cu metodele
    getString si getConstant care fac trecerea de la String la Constanta si invers.
  ### Pachetul Simulator
  #### Clasa Simulation
    Contine metodele care simuleaza runda initiala cat si o runda oarecare, dar si 
    metoda prin care este construit istoricul fiecarui producator.
  ### Clasa Main
    In clasa main este apelata metoda simulate a instantei de simulation care parcurge
    intreaga simulare apeland metode interne din instanta de simulation si se face scrierea
    in fisierul json de iesire.
## Relatii intre clase
  ### Distributor-Producer
  Clasa Distributor este in relatie Observer, Observable cu Producer. Atunci cand o instanta
  de Producer apeleaza metoda de setEnergyPerDistributor, toti observatorii acestuia sunt
  notificati adica toti distribuitorii cu care are contract la momentul acela. Producatorii
  avand o lista de contracte cu fiecare distribuitor caruia ii furnizeaza energie in acea luna,
  iar distribuitorii avand o lista de contracte cu cati producatori e nevoie pentru as acoperi
  necesarul de energie.
  ### Distributor-Consumer
  Sunt legati prin clasa ContractConsumer care are id-urile consumatorului si distribuitorului 
  legati prin contract, lunile ramase de contract si pretul ce trebuie virat in conturile 
  distribuitorilor de catre consumatori.
  ### Distribuitor-Strategy
  Fiecare distribuitor are o instanta de strategy specifica particularitatilor acestuia, care
  sorteaza producatorii in functie de strategia aleasa, strategie creata cu EntityFactory si 
  pasata distribuitorului in functie de parametrul strategyType din constructor. 
  
  
