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
![Architettura](src/DocumentationImages/architecture.png)
# Requisiti <a name="requisiti"></a>

## Requisiti non funzionali <a name="rnf"></a>
* Il sistema deve essere affidabile nella sua interezza. Di ogni ogni componente (hardware e software) deve essere assicurato il corretto funzionamento a intervalli  regolari di un minuto. In caso di irregolarità, l'utente deve essere immediatamente avvisato dell'inoperatività del dispositivo
* Le quantità di insulina rilasciate devono mantenere la glicemia entro valori sicuri in base allo stato dell'utente:  
&emsp;- a digiuno: tra i 72 ed i 126 mg/dl  
&emsp;- almeno 90 minuti dopo i pasti : tra i 90 ed i 153 mg/dl  
* Deve essere permesso al sistema di rilasciare insulina quando l'utente lo richieda
* Le misurazioni di glicemia devono essere effettuate regolarmente a intervalli di 5 minuti, in quanto i cambiamenti del livello di glucosio nel sangue ne richiedono diversi
* La durata della batteria deve essere massimizzata

## Requisiti funzionali <a name="rf"></a>
* Ogni componente hardware del sistema (pompa, sensore e batteria) deve essere in grado, tramite i relativi driver, di eseguire un'autodiagnostica e di ritornare al software di controllo lo stato di funzionamento
* Il sistema deve avvertire l'utente quando le scorte di insulina sono prossime a terminare. Lo stesso per quanto riguarda il livello di batteria del dispositivo
* Il sistema deve avvertire l'utente di glicemia alta o bassa
* Dopo i pasti il sistema permette all'utente di inserire i carboidrati ingeriti in modo da rilasciare la giusta quantità di insulina richiesta per mantenere la glicemia in un intervallo sicuro
# Scenari <a name="scenari"></a>

# Design <a name="design"></a>

# Quality assurance <a name="qa"></a>
