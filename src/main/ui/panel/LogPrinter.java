package ui.panel;

import exception.LogException;
import model.EventLog;


public interface LogPrinter {
    void printLog(EventLog el) throws LogException;
}
