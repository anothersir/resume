package com.mycompany.resumewebapp.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

public class ControllerUtil {

    public static void errorPage (HttpServletResponse response, Exception e){
        try {
            e.printStackTrace();
            response.sendRedirect("error?msg=" + e.getMessage());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
