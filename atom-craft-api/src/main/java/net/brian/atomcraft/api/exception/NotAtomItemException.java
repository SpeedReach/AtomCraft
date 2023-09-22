package net.brian.atomcraft.api.exception;

public class NotAtomItemException extends Exception{

    @Override
    public String getMessage() {
        return "Item is not an AtomItem!";
    }

}
