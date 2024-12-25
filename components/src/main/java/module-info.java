module devtools.components {
  exports de.hhn.it.devtools.components.connectfour.provider;
  requires org.slf4j;
  requires devtools.apis;
  requires java.sql;
  provides de.hhn.it.devtools.apis.connectfour.ConnectFourService
          with de.hhn.it.devtools.components.connectfour.provider.CfService;
        }
