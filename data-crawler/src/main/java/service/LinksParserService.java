package service;

import domain.Medical;

import java.util.Map;

public interface LinksParserService {
    public Map<Medical, String> parse();
}
