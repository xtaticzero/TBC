package mx.gob.sat.mat.tabacos.modelo.dao.impl;


import java.util.List;

import mx.gob.sat.mat.tabacos.modelo.dao.RepresentanteLegalDao;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.RepresentanteLegalMapper;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RepLegalDaoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Qualifier("representanteLegalDaoImpl")
public class RepresentanteLegalDaoImpl implements RepresentanteLegalDao {

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    @Override
    public RepresentanteLegal consultarPorRfc(String rfc) throws RepLegalDaoException {
        RepresentanteLegal representanteLegal = null;
        try {
            List<RepresentanteLegal> representantes;
            representantes = jdbcTemplate.query(SQL_REPLEGAL_RFC, new Object[]{rfc}, new RepresentanteLegalMapper());

            if (representantes != null && representantes.size() > 0) {
                representanteLegal = representantes.get(0);
            }
        } catch (DataAccessException e) {
            throw new RepLegalDaoException("Error al consultar los datos de Tabaclaera por RFC" + e.getMessage(), e);
        }
        return representanteLegal;
    }
}
