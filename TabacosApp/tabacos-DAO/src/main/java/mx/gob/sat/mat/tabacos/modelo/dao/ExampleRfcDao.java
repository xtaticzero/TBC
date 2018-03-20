/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.Date;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface ExampleRfcDao {
    Date getFecha() throws DaoException;
    
}
