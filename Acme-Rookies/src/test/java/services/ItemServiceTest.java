
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ItemServiceTest extends AbstractTest {

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ProviderService	providerService;


	@Override
	@Before
	public void setUp() {
		this.unauthenticate();
	}

	/**
	 * This test reefer to use case 10.1(Acme-Rookie)
	 * here we're going to test the create/edit of items
	 * Two positives
	 * Sixteen negatives
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void saveItemDriver() {
		final List<String> urls = new ArrayList<>();
		urls.add("https://asdf.com");
		final List<String> invalidUrls = new ArrayList<>();
		urls.add("adfg");

		final Object testingData[][] = {

			//Correct create
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", new Item(), "name", "description", urls, urls, null
			},
			//Correct edit
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", this.itemService.findOne(this.getEntityId("item00")), "name", "description", urls, urls, null
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(administrator)
				 * c) 33.33%
				 * d)
				 * 
				 */
				"admin", new Item(), "name", "description", urls, urls, IllegalArgumentException.class
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(rookie)
				 * c) 33.33%
				 * d)
				 * 
				 */
				"rookie0", new Item(), "name", "description", urls, urls, IllegalArgumentException.class
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(not logged)
				 * c) 33.33%
				 * d)
				 * 
				 */
				null, new Item(), "name", "description", urls, urls, IllegalArgumentException.class
			},

			//Blank name
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Name can't be blank
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", new Item(), "", "description", urls, urls, ConstraintViolationException.class
			},

			//Blank description
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Description can't be blank
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", new Item(), "name", "", urls, urls, ConstraintViolationException.class
			},

			//Null links
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) links can't be null
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", new Item(), "name", "description", null, urls, ConstraintViolationException.class
			},

			//Invalid links
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) links must be urls
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", new Item(), "name", "description", invalidUrls, urls, ConstraintViolationException.class
			},

			//Invalid pictures
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Pictures must be urls
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", new Item(), "name", "description", urls, invalidUrls, ConstraintViolationException.class
			},

			//Edit a item
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) A provider can't edit a item from another provider
				 * c) 83.33%
				 * d)
				 * 
				 */
				"provider1", this.itemService.findOne(this.getEntityId("item00")), "name", "description", urls, urls, IllegalArgumentException.class
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(administrator)
				 * c) 33.33%
				 * d)
				 * 
				 */
				"admin", this.itemService.findOne(this.getEntityId("item00")), "name", "description", urls, urls, IllegalArgumentException.class
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(rookie)
				 * c) 33.33%
				 * d)
				 * 
				 */
				"rookie0", this.itemService.findOne(this.getEntityId("item00")), "name", "description", urls, urls, IllegalArgumentException.class
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(not logged)
				 * c) 33.33%
				 * d)
				 * 
				 */
				null, this.itemService.findOne(this.getEntityId("item00")), "name", "description", urls, urls, IllegalArgumentException.class
			},

			//Blank name
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Name can't be blank
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", this.itemService.findOne(this.getEntityId("item00")), "", "description", urls, urls, ConstraintViolationException.class
			},

			//Blank description
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Description can't be blank
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", this.itemService.findOne(this.getEntityId("item00")), "name", "", urls, urls, ConstraintViolationException.class
			},

			//Null links
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) links can't be null
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", this.itemService.findOne(this.getEntityId("item00")), "name", "description", null, urls, ConstraintViolationException.class
			},

			//Invalid links
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Links must be urls
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", this.itemService.findOne(this.getEntityId("item00")), "name", "description", invalidUrls, urls, ConstraintViolationException.class
			},

			//Invalid pictures
			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Pictures must be urls
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", this.itemService.findOne(this.getEntityId("item00")), "name", "description", urls, invalidUrls, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.saveItemTemplate((String) testingData[i][0], (Item) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Collection<String>) testingData[i][4], (Collection<String>) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void saveItemTemplate(final String beanName, final Item item, final String name, final String description, final Collection<String> links, final Collection<String> pictures, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			if (item.getId() == 0)
				item.setProvider(this.providerService.findByPrincipal(LoginService.getPrincipal()));
			item.setName(name);
			item.setDescription(description);
			item.setLinks(links);
			item.setPictures(pictures);
			this.itemService.save(item);
			this.itemService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/**
	 * This test reefer to use case 10.1(Acme-Rookie)
	 * here we're going to test delete of items
	 * One positive
	 * Four negatives
	 */
	@Test
	public void deleteItemDriver() {
		final Object testingData[][] = {

			{
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Positive
				 * c) 100%
				 * d)
				 * 
				 */
				"provider0", this.itemService.findOne(this.getEntityId("item00")), null
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(administrator)
				 * c) 33.33%
				 * d)
				 * 
				 */
				"admin", this.itemService.findOne(this.getEntityId("item00")), IllegalArgumentException.class
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(rookie)
				 * c) 33.33%
				 * d)
				 * 
				 */
				"rookie1", this.itemService.findOne(this.getEntityId("item00")), IllegalArgumentException.class
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) Must be a provider(not logged)
				 * c) 33.33%
				 * d)
				 * 
				 */
				null, this.itemService.findOne(this.getEntityId("item00")), IllegalArgumentException.class
			}, {
				/**
				 * a) 10.1(Acme-Rookie): Providers can manage his or her catalogue of items, which includes listing, showing, creating, updating, and deleting them
				 * b) A provider can't delete a item of another provider
				 * c) 83.33%
				 * d)
				 * 
				 */
				"provider1", this.itemService.findOne(this.getEntityId("item00")), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteItemTemplate((String) testingData[i][0], (Item) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteItemTemplate(final String beanName, final Item item, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.itemService.delete(item);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
