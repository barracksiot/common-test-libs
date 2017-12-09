/*
 * MIT License
 *
 * Copyright (c) 2017 Barracks Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.barracks.commons.test;

import com.mongodb.*;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.barracks.commons.rest.HateoasRestTemplate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MongoMultiRepositoryTest {
    private static final String TEST_DATABASE = "test";
    private static final String MONGO_HOST = "localhost";

    private static MongodExecutable mongodExecutable;
    private static Mongo mongo;
    private final List<String> collectionNames;

    private DB db;
    private Map<String, DBCollection> collections = new HashMap<>();

    public MongoMultiRepositoryTest(String... collectionNames) {
        this.collectionNames = Arrays.asList(collectionNames);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        prepareMongo();
    }

    private static void prepareMongo() throws IOException {
        final MongodStarter starter = MongodStarter.getDefaultInstance();
        final IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .build();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongo = new MongoClient(MONGO_HOST, mongodConfig.net().getPort());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        try {
            mongodExecutable.stop();
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        db = mongo.getDB(TEST_DATABASE);
        for (String collectionName : collectionNames) {
            if (db.collectionExists(collectionName)) {
                db.getCollection(collectionName).drop();
            }
            collections.put(collectionName, db.createCollection(collectionName, new BasicDBObject()));
        }
    }

    public DBCollection getCollection(String collectionName) {
        return collections.get(collectionName);
    }

    @After
    public void tearDown() throws Exception {
        collections.clear();
        db.dropDatabase();
    }

    protected Mongo getMongo() {
        return mongo;
    }

    protected String getDatabaseName() {
        return TEST_DATABASE;
    }

}
