package com.ententee.interview.services;

import java.util.Map;

/**
 * Interface for the document service
 *
 * @author Alvaro Silva
 */
public interface DocumentService {

    byte[] generateDocument(Map<String, String> body);
}
