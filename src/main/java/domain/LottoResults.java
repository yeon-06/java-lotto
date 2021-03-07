package domain;

import java.util.HashMap;
import java.util.Map;

public class LottoResults {

    private final Map<LottoRank, Long> lottoResults;

    public LottoResults(final Map<LottoRank, Long> results) {
        lottoResults = new HashMap<>(results);
    }

    public Money getTotalWinningMoney() {
        Money totalMoney = Money.ZERO;
        for (LottoRank lottoRank : lottoResults.keySet()) {
            Money totalPrize = lottoRank.calculateTotalPrize(lottoResults.get(lottoRank));
            totalMoney = totalMoney.add(totalPrize);
        }

        return totalMoney;
    }

    public Map<LottoRank, Long> getValues() {
        return new HashMap<>(lottoResults);
    }
}