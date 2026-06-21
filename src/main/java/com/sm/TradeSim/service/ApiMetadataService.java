package com.sm.TradeSim.service;

import com.sm.TradeSim.config.AppProperties;
import com.sm.TradeSim.dto.ApiMetadataResponse;
import com.sm.TradeSim.dto.AttributeConfig;
import com.sm.TradeSim.dto.EndpointDescriptor;
import com.sm.TradeSim.dto.PaginationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiMetadataService {

    private final AppProperties appProperties;

    public ApiMetadataResponse getMetadata() {
        return ApiMetadataResponse.builder()
                .endpoints(buildEndpoints())
                .pagination(PaginationConfig.builder()
                        .maxPageSize(appProperties.getPagination().getMaxPageSize())
                        .defaultPageSize(appProperties.getPagination().getDefaultPageSize())
                        .build())
                .attributes(buildAttributes())
                .defaultSymbols(appProperties.getDefaultSymbols())
                .build();
    }

    private List<EndpointDescriptor> buildEndpoints() {
        return List.of(
                EndpointDescriptor.builder()
                        .id("global-quote")
                        .name("Global Quote")
                        .method("GET")
                        .path("/analysis/security/quote")
                        .description("Fetch live global quote from Alpha Vantage")
                        .queryParams(List.of("symbol"))
                        .build(),
                EndpointDescriptor.builder()
                        .id("list-orders")
                        .name("List Orders")
                        .method("GET")
                        .path("/analysis/orders")
                        .description("Paginated order blotter")
                        .queryParams(List.of("page", "size"))
                        .build(),
                EndpointDescriptor.builder()
                        .id("create-order")
                        .name("Create Order")
                        .method("POST")
                        .path("/analysis/orders")
                        .description("Submit a new trade order")
                        .queryParams(List.of())
                        .build(),
                EndpointDescriptor.builder()
                        .id("generate-dummy-orders")
                        .name("Generate Dummy Orders")
                        .method("POST")
                        .path("/analysis/orders/generate")
                        .description("Create sample orders for dashboard testing")
                        .queryParams(List.of())
                        .build(),
                EndpointDescriptor.builder()
                        .id("admin-user")
                        .name("Current Admin User")
                        .method("GET")
                        .path("/analysis/admin")
                        .description("Dummy admin profile (login not required yet)")
                        .queryParams(List.of())
                        .build()
        );
    }

    private Map<String, AttributeConfig> buildAttributes() {
        Map<String, AttributeConfig> attributes = new LinkedHashMap<>();

        attributes.put("symbol", AttributeConfig.builder()
                .label("Symbol")
                .type("string")
                .required(true)
                .min(null)
                .max(null)
                .allowedValues(appProperties.getDefaultSymbols().toArray(String[]::new))
                .build());

        attributes.put("quantity", AttributeConfig.builder()
                .label("Quantity")
                .type("number")
                .required(true)
                .min(1)
                .max(100000)
                .allowedValues(null)
                .build());

        attributes.put("price", AttributeConfig.builder()
                .label("Price")
                .type("number")
                .required(true)
                .min(0.01)
                .max(null)
                .allowedValues(null)
                .build());

        attributes.put("side", AttributeConfig.builder()
                .label("Side")
                .type("enum")
                .required(true)
                .min(null)
                .max(null)
                .allowedValues(new String[]{"BUY", "SELL"})
                .build());

        attributes.put("pageSize", AttributeConfig.builder()
                .label("Page Size")
                .type("number")
                .required(false)
                .min(1)
                .max(appProperties.getPagination().getMaxPageSize())
                .allowedValues(null)
                .build());

        return attributes;
    }
}
