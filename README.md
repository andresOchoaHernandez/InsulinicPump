# InsulinicPump
Progetto per fondamenti di ingegneria del software

# Contenuti
&emsp;[1.Descrizione progetto](#descrizione)  
&emsp;[2.Architettura generale](#architettura)  
&emsp;[3.Requisiti](#requisiti)   
&emsp;&emsp;[3.1.Requisiti non funzionali](#rnf)  
&emsp;&emsp;[3.2.Requisiti funzionali](#rf)  
&emsp;[4.Scenari](#scenari)  
&emsp;[5.Design](#design)  
&emsp;[6.Quality assurance](#qa)  
  

# Descrizone progetto <a name="descrizione"></a>
Sistema di somministrazione automatizzata di insulina per persone affette da diabete, che permette di mantenere la glicemia (sia a digiuno che dopo i pasti) entro un'intervallo sicuro.
Il dispositivo, a intervalli regolari di tempo, acquisisce il livello di glucosio nel sangue tramite un sensore, successivamente  calcola la dose correttiva di insulina ed infine la rilascia tramite una pompa.

# Architettura generale <a name="architettura"></a>
Questa è l'architettura ideata da per rispondere ai requisiti.  
L'intero applicativo consiste in un'applicazione spring boot che avvia in parallelo i due componenti principali, la view ed il main control loop.

![Architettura](src/DocumentationImages/architecture.png)

La scelta fondamentale che ha portato a questa strutturazione del software è quella di separare i componenti che gestiscono il dispositivo (il controllore) da quelli che visualizzano
i dati di quest'ultimo. La ragione sta nell'assunzione che il controllore abbia una maggiore importanza rispetto alla view, che può essere giustificata dalle seguenti ragioni:
* Il controllore interagisce direttamente con i componenti che interagiscono fisicamente con l'utente  (sistema safety-critical)
* I cambiamenti nel livello di glucosio nel sangue non sono istantanei (richiedono parecchi minuti), e sono prevedibili.
* Il controllore potrebbe essere soggetto a dei vincoli di tempo di esecuzione in CPU, il che potrebbe portare all'impiego di librerie REAL TIME che richiedono particolari sistemi operativi e/o configurazioni

## Descrizione della view
La view è un web server che consta di due compnenti:
* ***rest controller***:  
riceve i dati forniti dal controllore tramite richieste HTTP POST e li salva nelle strutture dati condivise con il view controller tramite il meccanismo fornito da spring chiamato autowiring
  
* ***view controller***:  
legge le entry nelle strutture dati e le mette a disposizione delle due pagine HTML: chart e bolus, dove verranno visualizzate tramite javascript.  
Inoltre si sincronizza con il main control loop per il calcolo ed inoculazione della dose di insulina post-pasto, denominata **BOLUS**.


## Descrizione del device
Il device è il componente che fornisce l'implementazione del funzionamento del dispositivo. È strutturato nel seguente modo:
* **hardware**:  
astrazioni software che permettono di interagire con i dispositivi fisici (e.g. sensore della glicemia, pompa di iniezione etc...) tramite dei wrapper che interagiscono con i driver.
* **pump controller**:  
i cui attori principali sono: 
  * ***controllore***: implementa le funzionalità safety-critical del sistema
  * ***rest controller***: riceve l'input dell'utente per il calcolo del *BOLUS*
* ***main control loop***:  
istanzia un controllore e utilizza le sue funzionalità seguendo una procedura che può essere vista come una macchina a stati:  
![MainControlLoop](src/DocumentationImages/MainControlLoop.png)
* *START DEVICE*: esegue l'autodiagnostica dei componenti hardware per verificarne il corretto funzionamento
* *CONTROL LOOP*: legge i dati forniti dal sensore di glicemia, calcola la dose di insulina necessaria e la inocula. Riesegue l'autodiagnostica ed invia le informazioni del dispositivo alla view. Infine aggiorna il display.
* *STAND BY*: Il device aspetta la fine del timer risparmiando batteria
* *BOLUS*: quando l'utente richiede questa funzionalità tramite la view, l'esecuzione dello standby si blocca. Viene aperto un nuovo thread in cui , non appena l'utente avrà inserito i carboidrati ingeriti durante il pasto, verrà calcolata ed inoculata la dose correttiva di insulina 
* *STOP DEVICE*: viene spento il dispositivo
# Requisiti <a name="requisiti"></a>
## Requisiti non funzionali <a name="rnf"></a>
* Il sistema deve essere affidabile nella sua interezza. Di ogni ogni componente (hardware e software) deve essere assicurato il corretto funzionamento a intervalli  regolari di un minuto. In caso di irregolarità, l'utente deve essere immediatamente avvisato dell'inoperatività del dispositivo
* Le quantità di insulina rilasciate devono mantenere la glicemia entro valori sicuri: tra i 72 ed i 126 mg/dl   
* Deve essere permesso al sistema di rilasciare insulina quando l'utente lo richieda
* Le misurazioni di glicemia devono essere effettuate regolarmente a intervalli di 5 minuti, in quanto i cambiamenti del livello di glucosio nel sangue ne richiedono diversi
* La durata della batteria deve essere massimizzata

## Requisiti funzionali <a name="rf"></a>
* Ogni componente hardware del sistema (pompa, sensore e batteria) deve essere in grado, tramite i relativi driver, di eseguire un'autodiagnostica e di ritornare al software di controllo lo stato di funzionamento
* Il sistema deve avvertire l'utente quando le scorte di insulina sono prossime a terminare. Lo stesso per quanto riguarda il livello di batteria del dispositivo
* Il sistema deve avvertire l'utente di glicemia alta o bassa
* Dopo i pasti il sistema permette all'utente di inserire i carboidrati ingeriti in modo da rilasciare la giusta quantità di insulina richiesta per mantenere la glicemia in un intervallo sicuro
# Scenari <a name="scenari"></a>


# Quality assurance <a name="qa"></a>
