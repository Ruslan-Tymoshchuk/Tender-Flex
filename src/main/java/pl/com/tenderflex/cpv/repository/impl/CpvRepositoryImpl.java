package pl.com.tenderflex.cpv.repository.impl;

import static java.util.Optional.ofNullable;
import static org.springframework.dao.support.DataAccessUtils.singleResult;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.cpv.repository.CpvRepository;
import pl.com.tenderflex.cpv.repository.mapper.CpvMapper;
import pl.com.tenderflex.model.Cpv;

@Repository
@RequiredArgsConstructor
public class CpvRepositoryImpl implements CpvRepository {

    public static final String FIND_ALL_CPVS_QUERY = "SELECT id, code, description FROM cpvs";
    public static final String FIND_CPV_BY_ID_QUERY = "SELECT id, code, description FROM cpvs WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final CpvMapper cpvMapper;

    @Override
    public List<Cpv> findAll() {
        return jdbcTemplate.query(FIND_ALL_CPVS_QUERY, cpvMapper);
    }

    @Override
    public Optional<Cpv> findById(Integer id) {
        return ofNullable(singleResult(
                jdbcTemplate.query(FIND_CPV_BY_ID_QUERY, cpvMapper, id)
            ));
    }

}