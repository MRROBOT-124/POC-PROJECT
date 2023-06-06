package com.realtime.project.config;

import com.realtime.project.entity.Website;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.Instant;

/**
 * CUSTOM SEQUENCE GENERATOR
 */
public class WebsiteSequenceGenerator implements IdentifierGenerator {

    /**
     * FUNCTION TO GENERATE CUSTOM SEQUENCE IDENTIFIER
     * @param session The session from which the request originates
     * @param object the entity or collection (idbag) for which the id is being generated
     * @return "object"
     */
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        Website website = (Website) object;
//      WEBSITE NAME + TIMESTAMP USED TO GENERATE A NEW IDENTIFIER SEQUENCE
        return website.getWebsiteName() + "_" + Instant.now();
    }
}
