package com.webservice.soap.config;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "mensaje")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema mensajeSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("MensajePort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ejemplo.com/mensaje");
        wsdl11Definition.setSchema(mensajeSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema mensajeSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/mesaje.xsd"));
    }

    @Bean(name = "saludo")
    public DefaultWsdl11Definition defaultWsdl11Definition2(XsdSchema saludoSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("SaludoPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ejemplo.com/saludo");
        wsdl11Definition.setSchema(saludoSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema saludoSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/saludo.xsd"));
    }

    @Bean(name = "pagosFondos")
    public DefaultWsdl11Definition defaultWsdl11Definition3(XsdSchema pagosFondosShema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("fondos");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ejemplo.com/fondos");
        wsdl11Definition.setSchema(pagosFondosShema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema pagosFondosShema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/fondos.xsd"));
    }
}
