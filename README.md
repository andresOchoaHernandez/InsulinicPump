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

# Architettura generale <a name="architettura"></a>

# Requisiti <a name="requisiti"></a>

## Requisiti non funzionali <a name="rnf"></a>
* Il sistema deve essere affidabile nella sua interezza. Di ogni ogni componente (hardware e software) deve essere assicurato il corretto funzionamento a intervalli  regolari di un minuto. In caso di irregolarità, l'utente deve essere immediatamente avvisato dell'inoperatività del dispositivo
* Le quantità di insulina rilasciate devono mantenere la glicemia entro valori sicuri in base allo stato dell'utente:  
&emsp;- a digiuno: tra i 72 ed i 126 mg/dl  
&emsp;- almeno 90 minuti dopo i pasti : tra i 90 ed i 153 mg/dl  
* Deve essere permesso al sistema di rilasciare insulina quando l'utente lo richieda
* Le misurazioni di glicemia devono essere effettuate regolarmente a intervalli di 5 minuti, in quanto i cambiamenti del livello di glucosio nel sangue ne richiedono diversi
* La durata della batteria deve essere massimizzata
* La piattaforma web deve essere disponibile all'utente a prescindere dallo stato del dispositivo
* La piattaforma web deve garantire la sicurezza e riservatezza dei dati di ogni utente
## Requisiti funzionali <a name="rf"></a>
* Ogni componente hardware del sistema (pompa, sensore e batteria) deve essere in grado, tramite i relativi driver, di eseguire un'autodiagnostica e di ritornare al software di controllo lo stato di funzionamento
* Il sistema deve avvertire l'utente quando le scorte di insulina sono prossime a terminare. Lo stesso per quanto riguarda il livello di batteria del dispositivo
* Il sistema deve avvertire l'utente di glicemia alta o bassa
* Dopo i pasti il sistema permette all'utente di inserire i carboidrati ingeriti in modo da rilasciare la giusta quantità di insulina richiesta per mantenere la glicemia in un intervallo sicuro
* Deve essere possibile per l'utente creare un account nella piattaforma web che gli permetta di registrare il proprio dispositivo. Ogni 12 ore il dispositivo invierà i dati alla piattaforma (statistiche di funzionamento i.e: durata di attività, storico di valori glicemici e unità di insulina, versione del software). 
* La piattaforma dovrà permettere all'utente di visulizzare lo storico delle informazioni del dispositivo, inoltre la possibilità di richiedere assistenza e/o scorte di insulina e aggiornare il software del dispositivo qualora sia disponibile una nuova versione

# Scenari <a name="scenari"></a>

# Design <a name="design"></a>

# Quality assurance <a name="qa"></a>
