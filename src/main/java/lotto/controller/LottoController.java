package lotto.controller;

import java.util.ArrayList;
import java.util.List;
import lotto.domain.LottoAmount;
import lotto.domain.LottoNumber;
import lotto.domain.LottoNumbers;
import lotto.domain.LottoTicket;
import lotto.domain.ManualLottoCount;
import lotto.domain.WinningNumbers;
import lotto.domain.WinningResult;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    public void start() {
        LottoAmount amount = inputAmount();

        // TODO ManualLottoCount input 받도록 수정
        LottoTicket lottoTicket = buyTicket(amount, new ManualLottoCount(2, amount.calculateLottoCount()));

        WinningNumbers winningNumbers = createWinningNumbers();

        WinningResult winningResult = getWinningResult(lottoTicket, winningNumbers);

        printResult(amount, winningResult);
    }

    private LottoAmount inputAmount() {
        try {
            return new LottoAmount(InputView.inputAmount());
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return inputAmount();
        }
    }

    private LottoTicket buyTicket(LottoAmount amount, ManualLottoCount manualLottoCount) {
        int manualTicketCount = manualLottoCount.getValue();
        int autoTicketCount = amount.calculateLottoCount() - manualTicketCount;

        LottoTicket lottoTicket = new LottoTicket(autoTicketCount);

        if (manualTicketCount != 0) {
            lottoTicket.buyManualTicket(buyManualTicket(manualLottoCount));
        }

        printTickets(manualTicketCount, autoTicketCount, lottoTicket);
        return lottoTicket;
    }

    private List<LottoNumbers> buyManualTicket(ManualLottoCount manualLottoCount) {
        OutputView.printInputManualTicketSentence();

        List<LottoNumbers> manualTickets = new ArrayList<>();
        int tryCount = manualLottoCount.getValue();
        for (int i = 0; i < tryCount; i++) {
            manualTickets.add(getInputLottoNumbers());
        }
        return manualTickets;
    }

    private void printTickets(int manualTryCount, int autoTryCount, LottoTicket lottoTicket) {
        OutputView.printTicketCount(manualTryCount, autoTryCount);
        OutputView.printTicket(lottoTicket);
    }

    private WinningNumbers createWinningNumbers() {
        OutputView.printInputWinningTicketSentence();
        LottoNumbers inputLottoNumbers = getInputLottoNumbers();
        LottoNumber bonusNumber = getBonusNumber();

        return getWinningNumbers(inputLottoNumbers, bonusNumber);
    }

    private LottoNumbers getInputLottoNumbers() {
        try {
            return new LottoNumbers(InputView.inputLottoNumbers());
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return getInputLottoNumbers();
        }
    }

    private LottoNumber getBonusNumber() {
        try {
            return LottoNumber.of(InputView.inputBonusBall());
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return getBonusNumber();
        }
    }

    private WinningNumbers getWinningNumbers(LottoNumbers lottoNumbers, LottoNumber bonusNumber) {
        try {
            return new WinningNumbers(lottoNumbers, bonusNumber);
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return getWinningNumbers(lottoNumbers, getBonusNumber());
        }
    }

    private WinningResult getWinningResult(LottoTicket lottoTicket, WinningNumbers winningNumbers) {
        return lottoTicket.calculateWinningStatistic(winningNumbers);
    }

    private void printResult(LottoAmount amount, WinningResult winningResult) {
        OutputView.printResultIntro();
        OutputView.printWinningStatistic(winningResult);
        OutputView.printProfit(amount.calculateProfit(winningResult.calculatePrizeSum()));
    }

}
