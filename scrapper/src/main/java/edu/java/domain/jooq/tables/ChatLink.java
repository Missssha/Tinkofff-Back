package edu.java.domain.jooq.tables;

import edu.java.domain.jooq.DefaultSchema;
import edu.java.domain.jooq.Keys;
import edu.java.domain.jooq.tables.records.ChatLinkRecord;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ChatLink extends TableImpl<ChatLinkRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>CHAT_LINK</code>
     */
    public static final ChatLink CHAT_LINK = new ChatLink();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<ChatLinkRecord> getRecordType() {
        return ChatLinkRecord.class;
    }

    /**
     * The column <code>CHAT_LINK.ID</code>.
     */
    public final TableField<ChatLinkRecord, Long> ID =
        createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>CHAT_LINK.CHAT_ID</code>.
     */
    public final TableField<ChatLinkRecord, Long> CHAT_ID =
        createField(DSL.name("CHAT_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>CHAT_LINK.LINK_ID</code>.
     */
    public final TableField<ChatLinkRecord, Long> LINK_ID =
        createField(DSL.name("LINK_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    private ChatLink(Name alias, Table<ChatLinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private ChatLink(Name alias, Table<ChatLinkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>CHAT_LINK</code> table reference
     */
    public ChatLink(String alias) {
        this(DSL.name(alias), CHAT_LINK);
    }

    /**
     * Create an aliased <code>CHAT_LINK</code> table reference
     */
    public ChatLink(Name alias) {
        this(alias, CHAT_LINK);
    }

    /**
     * Create a <code>CHAT_LINK</code> table reference
     */
    public ChatLink() {
        this(DSL.name("CHAT_LINK"), null);
    }

    public <O extends Record> ChatLink(Table<O> child, ForeignKey<O, ChatLinkRecord> key) {
        super(child, key, CHAT_LINK);
    }

    @Override
    @NotNull
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public Identity<ChatLinkRecord, Long> getIdentity() {
        return (Identity<ChatLinkRecord, Long>) super.getIdentity();
    }

    @Override
    @NotNull
    public UniqueKey<ChatLinkRecord> getPrimaryKey() {
        return Keys.PK_CHAT_LINK;
    }

    @Override
    @NotNull
    public List<UniqueKey<ChatLinkRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_8);
    }

    @Override
    @NotNull
    public ChatLink as(String alias) {
        return new ChatLink(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public ChatLink as(Name alias) {
        return new ChatLink(alias, this);
    }

    @Override
    @NotNull
    public ChatLink as(Table<?> alias) {
        return new ChatLink(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public ChatLink rename(String name) {
        return new ChatLink(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public ChatLink rename(Name name) {
        return new ChatLink(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public ChatLink rename(Table<?> name) {
        return new ChatLink(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super Long, ? super Long, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(
        Class<U> toType,
        Function3<? super Long, ? super Long, ? super Long, ? extends U> from
    ) {
        return convertFrom(toType, Records.mapping(from));
    }
}