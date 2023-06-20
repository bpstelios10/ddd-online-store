package org.stelios.courses.ddd.products.infrastructure.springweb.filters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ExtendWith(MockitoExtension.class)
class RequestIdFilterTest {

    private static final String REQUEST_ID_HEADER = "RequestId";

    private final RequestIdFilter requestIdFilter = new RequestIdFilter();

    @Mock
    private HttpServletRequest request;
    @Mock
    private FilterChain chain;

    @Test
    void doFilter_addsValidUuidAsRequestIdHeaderInResponse_whenRequestNotHaveIt() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        assertThat(response.getHeaderValue(REQUEST_ID_HEADER)).isNotNull();
        assertDoesNotThrow(() ->
                UUID.fromString(response.getHeaderValue(REQUEST_ID_HEADER).toString()).toString()
        );
    }

    @Test
    void doFilter_addsSameRequestIdHeaderInResponse_whenRequestHasValidOne() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        UUID requestIdValue = UUID.randomUUID();
        request1.addHeader(REQUEST_ID_HEADER, requestIdValue);
        MockHttpServletResponse response = new MockHttpServletResponse();
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        assertThat(response.getHeaderValue(REQUEST_ID_HEADER)).isEqualTo(requestIdValue.toString());
    }

    @Test
    void doFilter_addsNewValidUuidAsRequestIdHeaderInResponse_whenRequestHasInvalidOne() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        request1.addHeader(REQUEST_ID_HEADER, "random");
        MockHttpServletResponse response = new MockHttpServletResponse();
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        assertThat(response.getHeaderValue(REQUEST_ID_HEADER)).isNotEqualTo("random");
        assertDoesNotThrow(() ->
                UUID.fromString(response.getHeaderValue(REQUEST_ID_HEADER).toString()).toString()
        );
    }

    @Test
    void doFilter_setsContentTypeHeaderTo_whenResponseHasErrorCode() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(500);
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        assertThat(response.getHeaderValue(REQUEST_ID_HEADER)).isNotNull();
        assertThat(response.getHeaderValue(CONTENT_TYPE)).isEqualTo(APPLICATION_JSON_VALUE);
    }

    @Test
    void doFilter_doesNotSetContentTypeHeaderTo_whenResponseHasNonErrorCode() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(200);
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        assertThat(response.getHeaderValue(REQUEST_ID_HEADER)).isNotNull();
        assertThat(response.getHeaderValue(CONTENT_TYPE)).isNull();
    }

    @Test
    void getHeaderValue_returnsRequestedHeader() {
        List<String> headerNames = List.of("requestedHeader", "other-header");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(headerNames));
        when(request.getHeader("requestedHeader")).thenReturn("value");
        when(request.getHeader("other-header")).thenReturn("other-value");

        String response = requestIdFilter.getHeaderValue(request, "requestedHeader");

        assertThat(response).isEqualTo("value");
    }

    @Test
    void getHeaderValue_returnsRequestedHeader_caseInsensitive() {
        List<String> headerNames = List.of("requestedHeader", "other-header");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(headerNames));
        when(request.getHeader("requestedHeader")).thenReturn("value");
        when(request.getHeader("other-header")).thenReturn("other-value");

        String response = requestIdFilter.getHeaderValue(request, "requestedheader");

        assertThat(response).isEqualTo("value");
    }
}
