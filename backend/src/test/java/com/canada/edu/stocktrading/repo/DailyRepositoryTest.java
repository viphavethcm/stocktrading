package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.model.WatchList;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.repository.WatchlistRepository;
import com.canada.edu.stocktrading.service.utils.ConvertTimeUtils;
import com.canada.edu.stocktrading.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DailyRepositoryTest {
    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private EntityUtils utils;


    @Test
    public void testFindAllBySymbolIds(){
        WatchList randomWL = utils.generateRandomEntity(watchlistRepository,watchlistRepository.findAll().get(0).getWatchlistId());

        List<Integer>symbolIds = randomWL.getSymbols().stream().map(Symbol::getSymbolId).collect(Collectors.toList());

        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();

        List<Daily>dailies = dailyRepository.findDailiesBySymbolIds(ts, symbolIds);

        assertThat(dailies.size()).isEqualTo(symbolIds.size());
    }

    @Test
    public void testGetPriceBySymbolId(){
        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();
        Symbol randomSymbol = utils.generateRandomEntity(symbolRepository, symbolRepository.findAll().get(0).getSymbolId());
        BigDecimal price = dailyRepository.findCurrentPriceBySymbolId(ts, randomSymbol.getSymbolId());
        assertThat(price).isGreaterThan(new BigDecimal(0));
    }
}
