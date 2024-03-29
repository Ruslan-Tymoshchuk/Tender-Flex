package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OrganizationRepository;
import pl.com.tenderflex.model.Organization;

@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {

    public static final String ADD_NEW_ORGANIZATION_QUERY = "INSERT INTO "
            + "organizations(organization_name, national_registration_number, country_id, city, contact_person_id) VALUES (?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Organization create(Organization organization) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_ORGANIZATION_QUERY,
                    new String[] { "id" });
            statement.setString(1, organization.getName());
            statement.setString(2, organization.getNationalRegistrationNumber());
            statement.setInt(3, organization.getCountry().getId());
            statement.setString(4, organization.getCity());
            statement.setInt(5, organization.getContactPerson().getId());
            return statement;
        }, keyHolder);
        organization.setId(keyHolder.getKeyAs(Integer.class));
        return organization;
    }
}