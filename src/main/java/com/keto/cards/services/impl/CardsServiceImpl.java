package com.keto.cards.services.impl;

import com.keto.cards.entity.Cards;
import com.keto.cards.exception.CardAlreadyExistsException;
import com.keto.cards.repository.CardsRepository;
import com.keto.cards.services.ICardsService;
import com.keto.cards.utils.constants.CardsConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    @Autowired
    private CardsRepository cardsRepository;

    /**
     * Creates a new card for the customer with the given mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer.
     * @throws CardAlreadyExistsException if a card is already registered with the given mobile number.
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewcard(mobileNumber));
    }

    /**
     * Helper method to create a new card entity.
     *
     * @param mobileNumber The mobile number associated with the customer.
     * @return A new Cards entity.
     */
    private Cards createNewcard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
}
