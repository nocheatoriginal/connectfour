package de.hhn.it.devtools.components.connectfour;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.components.connectfour.provider.CfBoard;
import de.hhn.it.devtools.components.connectfour.provider.CfConsoleInterface;
import de.hhn.it.devtools.components.connectfour.provider.CfService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestCfConsoleInterface {
  ConnectFourService service;
  CfConsoleInterface ui;
  ConnectFourBoard board;

  @BeforeEach
  public void setup() {
    service = new CfService();
    ui = new CfConsoleInterface(service);
    board = new CfBoard();
  }

  @Test
  public void testPrintBoard() {
    ui.printBoard(board.getBoard());
  }

  @Test
  public void testPrintHelp() { ui.printHelp(); }

  @Test
  public void testHandleInputSave() {
    try {
      ui.handleInput("save");
    } catch (Exception e) {
      assertInstanceOf(UnsupportedOperationException.class, e);
    }
  }

  @Test
  public void testHandleInputLoad() {
    try {
      ui.handleInput("load");
    } catch (Exception e) {
      assertInstanceOf(UnsupportedOperationException.class, e);
    }
  }

  @Test
  public void testHandleInputInvalid() {
    try {
      ui.handleInput("invalid");
    } catch (Exception e) {
      assertInstanceOf(IllegalArgumentException.class, e);
    }
  }

  @Test
  public void testHandleInputInvalidNumber() {
    try {
      ui.handleInput("-1");
    } catch (Exception e) {
      assertInstanceOf(IllegalParameterException.class, e);
    }
  }

  @Test
  public void testHandleInputValidNumber() {
    ConnectFourService service = new CfService();
    try {
      ui.handleInput("1");
      ConnectFourBoard tmp = service.getBoard();
      assertNotEquals(tmp, new CfBoard());
    } catch (Exception e) {
      fail("Number should be valid input!");
    }
  }
}
