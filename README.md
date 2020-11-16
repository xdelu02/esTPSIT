# Chat

**Descrizione problema:**

Creare una chat che permetta la comunicazione tra più utenti e l’invio di messaggio a tutti gli utenti connessi e a utenti singoli.

**Risoluzione del problema**


**Funzionamento**
Se il Server è attivo, il client inserisce il nome utente e se non è già stato preso, può comunicare con gli altri.

**Messaggi scambiati**<br/>
@ls@ -> il client richiede al server la lista degli utenti connessi <br/>
@close@ -> il client dichiara al server l'intenzione di chiudere la connessione e quindi il socket <br/>
"messaggio" -> il client quando invia un messaggio senza # dichiara al server di voler inviare un messaggio a tutti <br/>
"messaggio#destinatario" -> il client quando invia un messaggio con # dichiara al server di voler inviare un messaggio ad un utente specifico<br/>

**Diagramma delle classi:**
https://lucid.app/documents/view/402e5944-76af-4bbe-88dd-722c498f8845

**Casi d'uso**
https://lucid.app/invitations/accept/a81cf188-9700-4e45-b182-cf1e65850dd1
