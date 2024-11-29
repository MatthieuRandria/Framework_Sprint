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

Sprint13:
    Niveau developper:
    Validation:
        afaka atao au niveau front
        formulaire mameno class iray , annotena au niveau champ (ex: int id required)
        throw Exception raha  tsy mety

Sprint14:
    Niveau developper:
    Validation:
        izay input misy erreur de averina miaraka @Orera
        return error eo @class input raha tsy remplit

        

authentication:
        annotation (admin,client)
        hoe tsy afaka mahazo ny fonctionnalite admin ny client par exemple