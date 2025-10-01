package com.richard.poise;

public class ProjectUpdateResult {
    private boolean success;
   private String message;

   public ProjectUpdateResult (boolean success, String message){
       this.success = success;
       this.message = message;
   }

   public boolean getSuccess(){
       return success;
   }
    public String getMessage(){
        return message;
    }

}
