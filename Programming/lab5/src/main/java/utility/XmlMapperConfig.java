package utility;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exceptions.NoSuchEnvironmentVariableException;
import io.BaseReader;
import io.BufferedReadWorker;

/**
 * A class for {@link XmlMapper} configuration.
 *
 * @author Mirax
 * @see XmlMapper
 * @since 1.0
 */
public class XmlMapperConfig {

    /**
     * Takes an instance of {@link XmlMapper} and configures it.
     * <p>
     * To handle ISO-8601 dates properly it uses {@link JavaTimeModule}.
     * </p>
     * @see XmlMapper
     *
     * @since 1.0
     */
    public XmlMapper ConfigureXmlMapper(XmlMapper xmlMapper) {
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return xmlMapper;
    }
}
