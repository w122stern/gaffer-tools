/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.gaffer.federated.rest;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import uk.gov.gchq.gaffer.federated.rest.serialisation.RestJsonProvider;
import uk.gov.gchq.gaffer.federated.rest.service.FederatedGraphConfigurationService;
import uk.gov.gchq.gaffer.federated.rest.service.FederatedOperationService;
import uk.gov.gchq.gaffer.federated.rest.service.SystemStatusService;
import uk.gov.gchq.gaffer.rest.service.GraphConfigurationService;
import java.util.HashSet;
import java.util.Set;

/**
 * An <code>FederatedApplicationConfig</code> sets up the application resources.
 */
public class ApplicationConfigForTest extends ResourceConfig {
    protected final Set<Class<?>> resources = new HashSet<>();

    public ApplicationConfigForTest() {
        addSystemResources();
        addServices();
        setupBeanConfig();

        register(new FederatedBinderForTest());

        registerClasses(resources);
    }

    protected void setupBeanConfig() {
        BeanConfig beanConfig = new BeanConfig();
        String baseUrl = System.getProperty(SystemProperty.BASE_URL, SystemProperty.BASE_URL_DEFAULT);
        if (!baseUrl.startsWith("/")) {
            baseUrl = "/" + baseUrl;
        }
        beanConfig.setBasePath(baseUrl);
        beanConfig.setVersion(System.getProperty(SystemProperty.VERSION, SystemProperty.CORE_VERSION));
        beanConfig.setResourcePackage(System.getProperty(SystemProperty.SERVICES_PACKAGE_PREFIX, SystemProperty.SERVICES_PACKAGE_PREFIX_DEFAULT));
        beanConfig.setScan(true);
    }

    protected void addServices() {
        resources.add(SystemStatusService.class);
        resources.add(FederatedOperationService.class);
        resources.add(FederatedGraphConfigurationService.class);
    }

    protected void addSystemResources() {
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);
        resources.add(RestJsonProvider.class);
    }
}
