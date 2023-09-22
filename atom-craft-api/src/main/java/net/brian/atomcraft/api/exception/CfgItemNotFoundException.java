package net.brian.atomcraft.api.exception;

public class CfgItemNotFoundException extends Exception {

    final String cfgId;
    public CfgItemNotFoundException(String cfgId){
        this.cfgId = cfgId;
    }

    @Override
    public String getMessage() {
        return "Item with configId " + cfgId + " not found in config!";
    }
}
