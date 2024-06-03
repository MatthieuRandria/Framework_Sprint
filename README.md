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
