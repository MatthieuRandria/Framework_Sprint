# Framework_Sprint
Sprint0:
    atao

Sprint1:
    (NB: Make sure it have Test directory)
    Tanjona: Maka ny liste des controllers annoté
    Fanaovana azy:
        -Mamorona Controller izay tokony annotena @: AnnotController (Atao anaty package ho an'ny controller)
        -Ho an'ny config(.xml):
            declarer nom du package des controllers (using 'init-param');
            declarer FrontServlet (Servlet associé a '/')
        Tester!

Sprint2:
    Tanjona: Mitady ny controller sy ny method mifanaraka @url omena
    Fanaovana azy:
        -Ao anaty controller izay efa annoté par @AnnotController, ajouter une methode qui sera annote par @Get(url = "/Zetianao") 
        -Ho an'ny config(.xml):
            declarer nom du package des controllers (using 'init-param');
            declarer FrontServlet (Servlet associé a '/')
        Tester!

Sprint3:
    Tanjona: Miantso ny method mifanaraka @Url 
    Fanaovana azy:
        -Ao anaty controller izay efa annoté par @AnnotController, ajouter une methode qui sera annote par @Get(url = "/Zetianao") 
        -Pour l'instant, set return type as "String" then output it.
        -Ho an'ny config(.xml):
            declarer nom du package des controllers (using 'init-param');
            declarer FrontServlet (Servlet associé a '/')
        Tester!

Sprint4:
    Tanjona: Mandefa mankany @View raha misy, sinon ,mprint ny raha String sinon, ERROR 
    Fanaovana azy:
        -Ao anaty controller izay efa annoté par @AnnotController, 
            ajouter une methode qui sera annote par @Get(url = "/Zetianao") et retourne ModelView
            Ho anle ModelView, ajouter ses attribut :
                modelView.setUrl(".jsp");
                modelView.add("clé",valeur);
        -Ho an'ny config(.xml):
            declarer nom du package des controllers (using 'init-param');
            declarer FrontServlet (Servlet associé a '/')
        Tester!

Sprint5:
    Tanjona: 
        -Gestion d'Erreur
            1. Raha tsy nahita controller dia throw
            2. Raha misy doublon ny URL annote @Get iray dia throw
            3. Raha tsy mireturn String na ModelView ny method annote par @Get dia throw
            4. Raha tsy ita ny view andefasana ModelView na tsisy mifanaraka @ controller, 404 not Found 
        -Ho an'ny config(.xml):
            declarer nom du package des controllers (using 'init-param');
            declarer FrontServlet (Servlet associé a '/')
        Tester!

Sprint6:
        Tanjona: 
            Manana formulaire, alaina ny donnees(ny args rehetra) formulaire, passena ny 
        Fanaovana azy:
        -'
        -Ho an'ny config(.xml):
            declarer nom du package des controllers (using 'init-param');
            declarer FrontServlet (Servlet associé a '/')
        Tester!

Sprint7:
        Tanjona: 
            Manana formulaire mandray Objet Annote par @Argument ho an ny args,
            alaina ny donnees(ny args rehetra)ao @ formulaire
            Pour preciser Attribut dia asiana ":" ny nom anle input (ex:  'Emp:nom')
        -Ho an'ny config(.xml):
            declarer nom du package des controllers (using 'init-param');
            declarer FrontServlet (Servlet associé a '/')
        Tester!

Sprint8:
        Tanjona:
            Mandray Session 
        -Fanaovana azy:
            Manana class MySession izay manana attribut HttpSession,
            Raha anampy valeur dia MySession.add("key",value)
        -Ho an'ny config(.xml):
            declarer nom du package des controllers (using 'init-param');
            declarer FrontServlet (Servlet associé a '/')
        Tester!

