/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author 415-pc-profe
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(servicios.AccesoResource.class);
        resources.add(servicios.AccesoRolResource.class);
        resources.add(servicios.LoginResource.class);
        resources.add(servicios.PersonaResource.class);
        resources.add(servicios.RolResource.class);
    }
    
}
