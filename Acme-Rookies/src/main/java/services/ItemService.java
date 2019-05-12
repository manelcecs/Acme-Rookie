
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import security.LoginService;
import security.UserAccount;
import utiles.AuthorityMethods;
import domain.Item;
import domain.Provider;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository	itemRepository;

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private Validator		validator;


	public Item findOne(final int idItem) {
		return this.itemRepository.findOne(idItem);
	}

	public Collection<Item> findAll() {
		return this.itemRepository.findAll();
	}

	public Item save(final Item item) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("PROVIDER"));
		final UserAccount principal = LoginService.getPrincipal();
		final Provider provider = this.providerService.findByPrincipal(principal);
		Assert.isTrue(item.getProvider().getId() == provider.getId());
		return this.itemRepository.save(item);
	}

	public void delete(final Item item) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("PROVIDER"));
		final UserAccount principal = LoginService.getPrincipal();
		final Provider provider = this.providerService.findByPrincipal(principal);
		Assert.isTrue(item.getProvider().getId() == provider.getId());
		this.itemRepository.delete(item);
	}

	public void flush() {
		this.itemRepository.flush();
	}

	public Collection<Item> getItemsOfProvider(final int idProvider) {
		return this.itemRepository.getItemsOfProvider(idProvider);
	}

	public Item reconstruct(final Item item, final BindingResult binding) {
		final Provider provider = this.providerService.findByPrincipal(LoginService.getPrincipal());

		Item itemRec;
		if (item.getId() == 0) {
			itemRec = new Item();
			itemRec.setProvider(provider);
		} else
			itemRec = this.itemRepository.findOne(item.getId());

		itemRec.setName(item.getName());
		itemRec.setDescription(item.getDescription());
		itemRec.setLinks(item.getLinks());
		itemRec.setPictures(item.getPictures());

		this.validator.validate(itemRec, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return itemRec;
	}

}
