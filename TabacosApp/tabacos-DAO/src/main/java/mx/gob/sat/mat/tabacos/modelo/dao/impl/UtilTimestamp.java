package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class UtilTimestamp {

    public Timestamp getTimeStamp(Date date) {
        Timestamp timestamp = null;
        if (date != null) {
            timestamp = new Timestamp(date.getTime());
        }
        return timestamp;
    }

    public Date getDate(Timestamp timestamp) {
        Date date = null;
        if (timestamp != null) {
            date = new Date(timestamp.getTime());
        }
        return date;
    }
}
