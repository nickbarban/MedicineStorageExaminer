package service;

import domain.Medical;

import java.util.Map;

public interface SiteParserService {
    public Map<Medical, String> parse();
}
