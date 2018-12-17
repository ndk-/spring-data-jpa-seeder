package com.example.seeder;

import com.example.seeder.entity.Company;
import com.example.seeder.entity.Person;
import com.example.seeder.repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class SeederApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    @Transactional
    public void postToCompany_shouldPreserveCompanyWithPeople() throws Exception {

        InputStream fixtureStream = new ClassPathResource("fixture/company.json").getInputStream();
        String companyJSONFixture = StreamUtils.copyToString(fixtureStream, Charset.forName("UTF-8"));

        mockMvc.perform(post("/company")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(companyJSONFixture)
        )
                .andExpect(status().isCreated());

        Company actualCompany = companyRepository.findAll().get(0);
        Company expectedCompany = objectMapper.readValue(companyJSONFixture, Company.class);

        assertThat(actualCompany)
                .isEqualToIgnoringGivenFields(expectedCompany, "id", "people");

        List<Person> actualCompanyPeople = actualCompany.getPeople();
        List<Person> expectedCompanyPeople = expectedCompany.getPeople();

        assertThat(actualCompanyPeople)
                .usingElementComparatorIgnoringFields("id", "companyId")
                .isEqualTo(expectedCompanyPeople);
    }

}

