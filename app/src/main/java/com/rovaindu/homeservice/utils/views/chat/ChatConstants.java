package com.rovaindu.homeservice.utils.views.chat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ChatConstants {
    public static final String DATABASE_NAME = "CometChatSDK.db";
    public static final int DATABASE_VERSION = 1;
    public static final String MODE_DEFAULT = "DEFAULT";
    public static final String MODE_HIGH_VOL = "HIGH_VOL";
    public static final String MODE_FLAKY = "FLAKY";
    public static final String AUDIO_MODE_SPEAKER = "AudioModeSpeaker";
    public static final String AUDIO_MODE_EARPIECE = "AudioModeEarpiece";
    public static final String RECEIVER_TYPE_USER = "user";
    public static final String RECEIVER_TYPE_GROUP = "group";
    public static final String CONVERSATION_TYPE_USER = "user";
    public static final String CONVERSATION_TYPE_GROUP = "group";
    public static final String MESSAGE_TYPE_TEXT = "text";
    public static final String MESSAGE_TYPE_IMAGE = "image";
    public static final String MESSAGE_TYPE_VIDEO = "video";
    public static final String MESSAGE_TYPE_AUDIO = "audio";
    public static final String MESSAGE_TYPE_FILE = "file";
    public static final String MESSAGE_TYPE_CUSTOM = "custom";
    public static final String USER_STATUS_ONLINE = "online";
    public static final String USER_STATUS_OFFLINE = "offline";
    public static final String GROUP_TYPE_PUBLIC = "public";
    public static final String GROUP_TYPE_PRIVATE = "private";
    public static final String GROUP_TYPE_PASSWORD = "password";
    public static final String AFFIX_PREPEND = "prepend";
    public static final String AFFIX_APPEND = "append";
    public static final String CATEGORY_MESSAGE = "message";
    public static final String CATEGORY_ACTION = "action";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_CUSTOM = "custom";
    public static final String SCOPE_ADMIN = "admin";
    public static final String SCOPE_MODERATOR = "moderator";
    public static final String SCOPE_PARTICIPANT = "participant";
    public static final String CALL_TYPE_AUDIO = "audio";
    public static final String CALL_TYPE_VIDEO = "video";
    public static final String CALL_STATUS_INITIATED = "initiated";
    public static final String CALL_STATUS_ONGOING = "ongoing";
    public static final String CALL_STATUS_UNANSWERED = "unanswered";
    public static final String CALL_STATUS_REJECTED = "rejected";
    public static final String CALL_STATUS_BUSY = "busy";
    public static final String CALL_STATUS_CANCELLED = "cancelled";
    public static final String CALL_STATUS_ENDED = "ended";
    public static final String WS_STATE_CONNECTED = "connected";
    public static final String WS_STATE_CONNECTING = "connecting";
    public static final String WS_STATE_DISCONNECTED = "disconnected";

    public ChatConstants() {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WSState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallType {
    }

    public static final class PubSubKeys {
        public static final String PUBSUB_GROBAL_PRESENCE = "global_presence";
        public static final String PUBSUB_SERVICE = "pubsub.%s";

        public PubSubKeys() {
        }
    }

    public static final class ConversationKeys {
        public static final String KEY_CONVERSATION_ID = "conversationId";
        public static final String KEY_CONVERSATION_TYPE = "conversationType";
        public static final String KEY_UNREAD_MESSAGE_COUNT = "unreadMessageCount";
        public static final String KEY_UPDATED_AT = "updatedAt";
        public static final String KEY_LAST_MESSAGE = "lastMessage";
        public static final String KEY_CONVERSATION_WITH = "conversationWith";

        public ConversationKeys() {
        }
    }

    public static final class AppInfoKeys {
        public static final String KEY_PLATFORM = "platform";
        public static final String KEY_USER_AGENT = "userAgent";
        public static final String KEY_DEVICE_ID = "deviceId";
        public static final String KEY_APP_INFO = "appInfo";
        public static final String KEY_APP_INFO_VERSION = "version";
        public static final String KEY_APP_INFO_API_VERSION = "apiVersion";
        public static final String KEY_APP_INFO_OS_VERSION = "osVersion";
        public static final String KEY_PLATFORM_ANDROID = "Android";
        public static final String KEY_APPINFO_RESOURCE = "resource";
        public static final String KEY_APPINFO_PLATFORM = "platform";
        public static final String KEY_APPINFO_LANGUAGE = "language";
        public static final String KEY_USER_AGENT_ANDROID = "cc_android_sdk";

        public AppInfoKeys() {
        }
    }

    public static final class XMPPKeys {
        public static final String XMPP_KEY_JABBER_CLIENT = "jabber:client";
        public static final String XMPP_KEY_DELIVERED_AT = "deliveredAt";
        public static final String XMPP_KEY_READ_AT = "readAt";
        public static final String XMPP_KEY_TIME = "time";
        public static final String XMPP_KEY_DELIVERED = "delivered";
        public static final String XMPP_KEY_READ = "read";
        public static final String XMPP_RECEIPTS_NAMESPACE = "urn:xmpp:receipts";
        public static final String KEY_FORWARD_NAMESPACE = "urn:xmpp:forward:0";
        public static final String KEY_FORWARDED = "forwarded";
        public static final String KEY_SENT = "sent";
        public static final String KEY_RECEIVED = "received";
        public static final String KEY_MUC_JOIN = "muc_join";
        public static final String KEY_MUC_JOIN_NAMESPACE = "cometchat.io.muc.join";
        public static final String KEY_SETTINGS = "settings";
        public static final String KEY_SETTINGS_NAMESPACE = "com.cometchat:settings";
        public static final String KEY_USER = "user";
        public static final String KEY_USER_NAMESPACE = "com.cometchat.models:User";
        public static final String KEY_COMPOSING = "composing";
        public static final String KEY_COMPOSING_NAMESPACE = "http://jabber.org/protocol/chatstates";
        public static final String KEY_PAUSED = "paused";
        public static final String KEY_PAUSED_NAMESPACE = "http://jabber.org/protocol/chatstates";

        public XMPPKeys() {
        }
    }

    public static final class XMPPStanzas {
        public static final String STANZA_DELIVERY_TAG = "<delivered xmlns='urn:xmpp:receipts' id='%s' receiverId='%s' type='%s'/>";
        public static final String STANZA_READ_TAG = "<read xmlns='urn:xmpp:receipts' id='%s' receiverId='%s' type='%s'/>";
        public static final String STANZA_USER_TAG = "<user xmlns='com.cometchat.models:User'>%s</user>";
        public static final String STANZA_COMPOSING_TAG = "<composing xmlns='http://jabber.org/protocol/chatstates'/>";
        public static final String STANZA_PAUSED_TAG = "<paused xmlns='http://jabber.org/protocol/chatstates'/>";

        public XMPPStanzas() {
        }
    }

    public static final class SuccessMessages {
        public static final String MESSAGE_INIT_SUCCESS = "Init Successful";
        public static final String MESSAGE_GROUP_JOIN_SUCCESS = "Group joined successfully.";
        public static final String MESSAGE_GROUP_LEAVE_SUCCESS = "Group left successfully.";
        public static final String MESSAGE_GROUP_DELETE_SUCCESS = "Group deleted successfully.";
        public static final String MESSAGE_MEMBER_KICKED_SUCCESS = "Group member kicked successfully";
        public static final String MESSAGE_MEMBER_BANNED_SUCCESS = "Group member banned successfully";
        public static final String MESSAGE_MEMBER_UNBANNED_SUCCESS = "Group member unbanned successfully.";
        public static final String MESSAGE_MEMBER_SCOPE_CHANGED_SUCCESS = "Group member scope changed successfully.";
        public static final String MESSAGE_LOGOUT_SUCCESS = "User logged out successfully.";
        public static final String MESSAGE_REGISTRATION_SUCCESS = "Token Registration successful.";

        public SuccessMessages() {
        }
    }

    public static final class ExtraKeys {
        public static final String DELIMETER_DOT = ".";
        public static final String DELIMETER_OPEN_SQUARE_BRACE = "[";
        public static final String DELIMETER_CLOSE_SQUARE_BRACE = "]";
        public static final String DELIMETER_AT = "@";
        public static final String DELIMETER_SLASH = "/";
        public static final String KEYWORD_UNAVAILABLE = "unavailable";
        public static final String KEY_GROUP_CHAT = "groupchat";
        public static final String KEY_HTTPS = "https://";
        public static final int TYPING_LIMIT = 5000;
        public static final String KEY_ANDROID = "android";
        public static final String KEY_SPACE = " ";
        public static final String KEY_ADD_MEMBER_BANNED = "usersToBan";
        public static final String KEY_ADD_MEMBER_ADMINS = "admins";
        public static final String KEY_ADD_MEMBER_PARTICIPANTS = "participants";
        public static final String KEY_ADD_MEMBER_MODERATORS = "moderators";

        public ExtraKeys() {
        }
    }

    public static final class CallKeys {
        public static final String CALL_ID = "id";
        public static final String CALL_SESSION_ID = "sessionid";
        public static final String CALL_RECEIVER = "receiver";
        public static final String CALL_SENDER = "sender";
        public static final String CALL_RECEIVER_TYPE = "receiverType";
        public static final String CALL_STATUS = "status";
        public static final String CALL_TYPE = "type";
        public static final String CALL_INITIATED_AT = "initiatedAt";
        public static final String CALL_JOINED_AT = "joinedAt";
        public static final String CALL_METADATA = "metadata";
        public static final String CALL_ENTITIES = "entities";
        public static final String CALL_ENTITY_TYPE = "entityType";
        public static final String CALL_ENTITY = "entity";
        public static final String CALL_ENTITY_USER = "user";
        public static final String CALL_ENTITY_GROUP = "group";

        public CallKeys() {
        }
    }

    public static final class ActionMessages {
        public static final String ACTION_GROUP_JOINED_MESSAGE = "%s joined";
        public static final String ACTION_GROUP_LEFT_MESSAGE = "%s left";
        public static final String ACTION_MEMBER_KICKED_MESSAGE = "%s kicked %s";
        public static final String ACTION_MEMBER_BANNED_MESSAGE = "%s banned %s";
        public static final String ACTION_MEMBER_UNBANNED_MESSAGE = "%s unbanned %s";
        public static final String ACTION_MESSAGE_EDITED_MESSAGE = "Message Edited";
        public static final String ACTION_MESSAGE_DELETED_MESSAGE = "Message Deleted";
        public static final String ACTION_MEMBER_ADDED_TO_GROUP = "%s added %s";
        public static final String ACTION_MEMBER_SCOPE_CHANGED = "%s made %s %s";

        public ActionMessages() {
        }
    }

    public static final class ActionKeys {
        public static final String KEY_BY = "by";
        public static final String KEY_ON = "on";
        public static final String KEY_FOR = "for";
        public static final String KEY_ENTITY_TYPE = "entityType";
        public static final String KEY_ENTITY = "entity";
        public static final String KEY_ENTITY_USER = "user";
        public static final String KEY_ENTITY_GROUP = "group";
        public static final String KEY_ENTITY_MESSAGE = "message";
        public static final String KEY_EXTRAS = "extras";
        public static final String KEY_SCOPE = "scope";
        public static final String KEY_OLD = "old";
        public static final String KEY_NEW = "new";
        public static final String ACTION_CREATED = "created";
        public static final String ACTION_UPDATED = "updated";
        public static final String ACTION_DELETED = "deleted";
        public static final String ACTION_JOINED = "joined";
        public static final String ACTION_LEFT = "left";
        public static final String ACTION_KICKED = "kicked";
        public static final String ACTION_BANNED = "banned";
        public static final String ACTION_UNBANNED = "unbanned";
        public static final String ACTION_SCOPE_CHANGED = "scopeChanged";
        public static final String ACTION_MESSAGE_EDITED = "edited";
        public static final String ACTION_MESSAGE_DELETED = "deleted";
        public static final String ACTION_MEMBER_ADDED = "added";
        public static final String ACTION_TYPE_USER = "user";
        public static final String ACTION_TYPE_GROUP = "group";
        public static final String ACTION_TYPE_GROUP_MEMBER = "groupMember";
        public static final String ACTION_TYPE_MESSAGE = "message";
        public static final String ACTION_TYPE_CALL = "call";

        public ActionKeys() {
        }
    }

    public static final class PaginationKeys {
        public static final String KEY_PAGINATION = "pagination";
        public static final String KEY_META = "meta";
        public static final String KEY_PAGE = "page";
        public static final String KEY_CURSOR = "cursor";
        public static final String KEY_AFFIX = "affix";
        public static final String KEY_UID = "uid";
        public static final String KEY_CURSOR_VALUE = "cursorValue";
        public static final String KEY_CURSOR_FIELD = "cursorField";
        public static final String KEY_PER_PAGE = "per_page";
        public static final String KEY_FIELD_TIMESTAMP = "sentAt";
        public static final String KEY_FIELD_MESSAGEID = "id";
        public static final String KEY_PAGINATION_CURRENT_PAGE = "current_page";
        public static final String KEY_PAGINATION_TOTAL_PAGES = "total_pages";

        public PaginationKeys() {
        }
    }

    public static final class PresenceResponse {
        public static final String PRESENCE_IQ = "<iq from='%s' id='CC^PRESENCE' type='get'><query xmlns='jabber:iq:presencereq' action='presence'>[%s]</query></iq>";
        public static final String PRESENCE_ELEMENT_NAME = "presence";
        public static final String PRESENCE_ATTRIBUTE_FROM = "from";
        public static final String PRESENCE_ATTRIBUTE_STATUS = "status";
        public static final String PRESENCE_ATTRIBUTE_LASTACTIVEAT = "lastActiveAt";
        public static final String PRESENCE_ATTRIBUTE_TYPE = "type";
        public static final String PRESENCE_STANZA_ID = "CC^PRESENCE";
        public static final String PRESENCE_CHILD_ELEMENT_NAME = "query";
        public static final String PRESENCE_CHILD_ELEMENT_NAMESPACE = "jabber:iq:presencereq";
        public static final String PRESENCE_PROVIDER_ELEMENT_NAME = "presences";
        public static final String PRESENCE_PROVIDER_ELEMENT_NAMESPACE = "jabber:client";

        public PresenceResponse() {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MemberScope {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Affix {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConversationTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ReceiverTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioModes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Modes {
    }

    public static final class SettingsKeys {
        public static final String SETTINGS_CHAT_HOST = "CHAT_HOST";
        public static final String SETTINGS_ADMIN_API_HOST = "ADMIN_API_HOST";
        public static final String SETTINGS_CLIENT_API_HOST = "CLIENT_API_HOST";
        public static final String SETTINGS_CHAT_USE_SSL = "XMPP_USE_SSL";
        public static final String SETTINGS_GROUP_SERVICE = "GROUP_SERVICE";
        public static final String SETTINGS_CALL_SERVICE = "CALL_SERVICE";
        public static final String SETTINGS_CHAT_WS_PORT = "CHAT_WS_PORT";
        public static final String SETTINGS_CHAT_WSS_PORT = "CHAT_WSS_PORT";
        public static final String SETTINGS_CHAT_HTTP_BIND_PORT = "CHAT_HTTP_BIND_PORT";
        public static final String SETTINGS_CHAT_HTTPS_BIND_PORT = "CHAT_HTTPS_BIND_PORT";
        public static final String SETTINGS_CONTACT_LIST = "contactList";
        public static final String WEBRTC_HOST = "WEBRTC_HOST";
        public static final String WEBRTC_USE_SSL = "WEBRTC_USE_SSL";
        public static final String WEBRTC_WS_PORT = "WEBRTC_WS_PORT";
        public static final String WEBRTC_WSS_PORT = "WEBRTC_WSS_PORT";
        public static final String WEBRTC_HTTP_BIND_PORT = "WEBRTC_HTTP_BIND_PORT";
        public static final String WEBRTC_HTTPS_BIND_PORT = "WEBRTC_HTTPS_BIND_PORT";
        public static final String SETTINGS_MODE = "MODE";
        public static final String SETTINGS_CHAT_HOST_OVERRIDE = "CHAT_HOST_OVERRIDE";
        public static final String SETTINGS_CHAT_HOST_APP_SPECIFIC = "CHAT_HOST_APP_SPECIFIC";
        public static final String SETTINGS_JID_HOST_OVERRIDE = "JID_HOST_OVERRIDE";
        public static final String SETTINGS_EXTENSIONS = "extensions";
        public static final String SETTINGS_DB_KEY = "cc_settings";
        public static final String SETTINGS_RTC_REGION_KEY = "RTC_REGION";

        public SettingsKeys() {
        }
    }

    public static final class GroupKeys {
        public static final String KEY_GROUP_GUID = "guid";
        public static final String KEY_GROUP_NAME = "name";
        public static final String KEY_GROUP_ICON = "icon";
        public static final String KEY_GROUP_DESCRIPTION = "description";
        public static final String KEY_GROUP_OWNER = "owner";
        public static final String KEY_GROUP_TYPE = "type";
        public static final String KEY_GROUP_PASSWORD = "password";
        public static final String KEY_METADATA = "metadata";
        public static final String KEY_CREATED_AT = "createdAt";
        public static final String KEY_UPDATED_AT = "updatedAt";
        public static final String KEY_HAS_JOINED = "hasJoined";
        public static final String KEY_GROUP_IDENTITY = "groupIdentity";
        public static final String KEY_GROUP_MEMBER_SCOPE = "scope";
        public static final String KEY_GROUP_MEMBER_JOINED_AT = "joinedAt";
        public static final String KEY_GROUP_MEMBERS_COUNT = "membersCount";

        public GroupKeys() {
        }
    }

    public static final class MessageKeys {
        public static final String KEY_SEND_MESSAGE_ID = "id";
        public static final String KEY_SEND_MESSAGE_MUID = "muid";
        public static final String KEY_SEND_TEXT_MESSAGE_TYPE = "type";
        public static final String KEY_SEND_TEXT_MESSAGE_TEXT = "text";
        public static final String KEY_SEND_TEXT_METADATA = "metadata";
        public static final String KEY_SEND_TEXT_RECEIVER_TYPE = "receiverType";
        public static final String KEY_SEND_MESSAGE_TYPE = "type";
        public static final String KEY_MESSAGE = "message";
        public static final String KEY_RECEIVER_UID = "receiver";
        public static final String KEY_RECEIVER_ID = "receiverId";
        public static final String KEY_SENDER = "sender";
        public static final String KEY_SENDER_UID = "senderUid";
        public static final String KEY_SENT_AT = "sentAt";
        public static final String KEY_MESSAGE_URL = "url";
        public static final String KEY_MESSAGE_FILE = "file";
        public static final String KEY_MESSAGE_STATUS = "status";
        public static final String KEY_MESSAGE_DELIVERED_AT = "deliveredAt";
        public static final String KEY_MESSAGE_READ_AT = "readAt";
        public static final String KEY_MESSAGE_EDITED_AT = "editedAt";
        public static final String KEY_MESSAGE_DELETED_AT = "deletedAt";
        public static final String KEY_MESSAGE_DELETED_BY = "deletedBy";
        public static final String KEY_MESSAGE_EDITED_BY = "editedBy";
        public static final String KEY_MESSAGE_CATEGORY = "category";
        public static final String KEY_MESSAGE_ATTACHMENTS = "attachments";
        public static final String KEY_ATTACHMENT_NAME = "name";
        public static final String KEY_ATTACHMENT_EXTENSION = "extension";
        public static final String KEY_ATTACHMENT_SIZE = "size";
        public static final String KEY_ATTACHMENT_MIMETYPE = "mimeType";
        public static final String KEY_ATTACHMENT_URL = "url";
        public static final String KEY_CUSTOM_SUB_TYPE = "subType";
        public static final String KEY_CUSTOM_CUSTOM_DATA = "customData";
        public static final String KEY_UPDATED_AT = "updatedAt";
        public static final String KEY_PARENT_MESSAGE_ID = "parentId";
        public static final String KEY_REPLY_COUNT = "replyCount";

        public MessageKeys() {
        }
    }

    public static final class Errors {
        public static final String ERROR_IO_EXCEPTION = "ERROR_IO_EXCEPTION";
        public static final String ERROR_JSON_EXCEPTION = "ERROR_JSON_EXCEPTION";
        public static final String ERROR_PASSWORD_MISSING = "ERROR_PASSWORD_MISSING";
        public static final String ERROR_LIMIT_EXCEEDED = "ERROR_LIMIT_EXCEEDED";
        public static final String ERROR_NON_POSITIVE_LIMIT = "ERROR_NON_POSITIVE_LIMIT";
        public static final String ERROR_USER_NOT_LOGGED_IN = "ERROR_USER_NOT_LOGGED_IN";
        public static final String ERROR_INVALID_GUID = "ERROR_INVALID_GUID";
        public static final String ERROR_INVALID_UID = "ERROR_INVALID_UID";
        public static final String ERROR_BLANK_UID = "ERROR_BLANK_UID";
        public static final String ERROR_UID_WITH_SPACE = "ERROR_UID_WITH_SPACE";
        public static final String ERROR_XMPP = "ERROR_XMPP";
        public static final String ERROR_CALL_NOT_INITIATED = "ERROR_CALL_NOT_INITIATED";
        public static final String ERROR_CALL = "ERROR_CALL";
        public static final String ERROR_CALL_SESSION_MISMATCH = "ERROR_CALL_SESSION_MISMATCH";
        public static final String ERROR_UID_GUID_NOT_SPECIFIED = "ERROR_UID_GUID_NOT_SPECIFIED";
        public static final String ERROR_INTERNET_UNAVAILABLE = "ERROR_INTERNET_UNAVAILABLE";
        public static final String ERROR_REQUEST_IN_PROGRESS = "ERROR_REQUEST_IN_PROGRESS";
        public static final String ERROR_FILTERS_MISSING = "ERROR_FILTERS_MISSING";
        public static final String ERROR_BLANK_AUTHTOKEN = "ERROR_BLANK_AUTHTOKEN";
        public static final String ERROR_EXTENSION_DISABLED = "ERROR_EXTENSION_DISABLED";
        public static final String ERROR_INVALID_MESSAGEID = "ERROR_INVALID_MESSAGEID";
        public static final String ERROR_INVALID_MESSAGE_TYPE = "ERROR_INVALID_MESSAGE_TYPE";
        public static final String ERROR_LIST_EMPTY = "ERROR_LIST_EMPTY";
        public static final String ERROR_UPDATESONLY_WITHOUT_UPDATEDAFTER = "ERROR_UPDATESONLY_WITHOUT_UPDATEDAFTER";
        public static final String ERROR_LOGOUT_FAIL = "ERROR_LOGOUT_FAIL";
        public static final String ERROR_EMPTY_APPID = "ERROR_EMPTY_APPID";
        public static final String ERROR_REGION_MISSING = "ERROR_REGION_MISSING";
        public static final String ERROR_APP_SETTINGS_NULL = "ERROR_APP_SETTINGS_NULL";
        public static final String ERROR_API_KEY_NOT_FOUND = "ERROR_API_KEY_NOT_FOUND";
        public static final String ERROR_MESSAGE_TEXT_EMPTY = "ERROR_MESSAGE_TEXT_EMPTY";
        public static final String ERROR_FILE_OBJECT_INVALID = "ERROR_FILE_OBJECT_INVALID";
        public static final String ERROR_FILE_URL_EMPTY = "ERROR_FILE_URL_EMPTY";
        public static final String ERROR_EMPTY_CUSTOM_DATA = "ERROR_EMPTY_CUSTOM_DATA";
        public static final String ERROR_EMPTY_GROUP_NAME = "ERROR_EMPTY_GROUP_NAME";
        public static final String ERROR_EMPTY_GROUP_TYPE = "ERROR_EMPTY_GROUP_TYPE";
        public static final String ERROR_LOGIN_IN_PROGRESS = "ERROR_LOGIN_IN_PROGRESS";
        public static final String ERROR_INVALID_MESSAGE = "ERROR_INVALID_MESSAGE";
        public static final String ERROR_INVALID_MESSAGE_ID = "ERROR_INVALID_MESSAGE_ID";
        public static final String ERROR_INVALID_GROUP = "ERROR_INVALID_GROUP";
        public static final String ERROR_INVALID_CALL = "ERROR_INVALID_CALL";
        public static final String ERROR_INVALID_CALL_TYPE = "ERROR_INVALID_CALL_TYPE";
        public static final String ERROR_INVALID_RECEIVER_TYPE = "ERROR_INVALID_RECEIVER_TYPE";
        public static final String ERROR_INVALID_SESSION_ID = "ERROR_INVALID_SESSION_ID";
        public static final String ERROR_ACTIVITY_NULL = "ERROR_ACTIVITY_NULL";
        public static final String ERROR_VIEW_NULL = "ERROR_VIEW_NULL";
        public static final String ERROR_UNHANDLED_EXCEPTION = "ERROR_UNHANDLED_EXCEPTION";
        public static final String ERROR_INVALID_FCM_TOKEN = "ERROR_INVALID_FCM_TOKEN";
        public static final String ERROR_FETECH_JOINED_GROUPS = "ERROR_FETECH_JOINED_GROUPS";
        public static final String ERROR_INVALID_GROUP_TYPE = "ERROR_INVALID_GROUP_TYPE";
        public static final String ERROR_CALL_IN_PROGRESS = "ERROR_CALL_IN_PROGRESS";
        public static final String ERROR_INCORRECT_INITIATOR = "ERROR_INCORRECT_INITIATOR";
        public static final String ERROR_GROUP_JOIN = "ERROR_GROUP_JOIN";
        public static final String ERROR_INVALID_USER_NAME = "ERROR_INVALID_USER_NAME";
        public static final String ERROR_INVALID_USER = "ERROR_INVALID_USER";
        public static final String ERROR_INVALID_GROUP_NAME = "ERROR_INVALID_GROUP_NAME";
        public static final String ERROR_INVALID_TIMESTAMP = "ERROR_INVALID_TIMESTAMP";
        public static final String ERROR_INVALID_CATEGORY = "ERROR_INVALID_CATEGORY";
        public static final String ERROR_EMPTY_ICON = "ERROR_EMPTY_ICON";
        public static final String ERROR_EMPTY_DESCRIPTION = "ERROR_EMPTY_DESCRIPTION";
        public static final String ERROR_EMPTY_METADATA = "ERROR_EMPTY_METADATA";
        public static final String ERROR_EMPTY_SCOPE = "ERROR_EMPTY_SCOPE";
        public static final String ERROR_INVALID_SCOPE = "ERROR_INVALID_SCOPE";
        public static final String ERROR_INVALID_CONVERSATION_WITH = "ERROR_INVALID_CONVERSATION_WITH";
        public static final String ERROR_INVALID_CONVERSATION_TYPE = "ERROR_INVALID_CONVERSATION_TYPE";
        public static final String ERROR_INVALID_MEDIA_MESSAGE = "ERROR_INVALID_MEDIA_MESSAGE";
        public static final String ERROR_INVALID_ATTACHMENT = "ERROR_INVALID_ATTACHMENT";
        public static final String ERROR_INVALID_FILE_NAME = "ERROR_INVALID_FILE_NAME";
        public static final String ERROR_INVALID_FILE_EXTENSION = "ERROR_INVALID_FILE_EXTENSION";
        public static final String ERROR_INVALID_FILE_MIME_TYPE = "ERROR_INVALID_FILE_MIME_TYPE";
        public static final String ERROR_INVALID_FILE_URL = "ERROR_INVALID_FILE_URL";
        public static final String ERROR_CONVERSATION_NOT_FOUND = "ERROR_CONVERSATION_NOT_FOUND";
        public static final String ERROR_GROUP_ALREADY_JOINED = "ERR_ALREADY_JOINED";
        public static final String ERROR_INIT_NOT_CALLED = "INIT_NOT_CALLED";
        public static final String ERROR_PASSWORD_MISSING_MESSAGE = "Password is mandatory for a protected group";
        public static final String ERROR_LIMIT_EXCEEDED_MESSAGE = "Limit Exceeded Max limit of %s";
        public static final String ERROR_USER_NOT_LOGGED_IN_MESSAGE = "Please log in to CometChat before calling this method";
        public static final String ERROR_INVALID_GUID_MESSAGE = "Please provide a valid GUID";
        public static final String ERROR_INVALID_UID_MESSAGE = "Please provide a valid UID";
        public static final String ERROR_BLANK_UID_MESSAGE = "UID cannot be blank. Please provide a valid UID";
        public static final String ERROR_UID_WITH_SPACE_MESSAGE = "UID cannot contain spaces. Please provide a valid UID";
        public static final String ERROR_DEFAULT_MESSAGE = "Something went wrong";
        public static final String ERROR_CALL_NOT_INITIATED_MESSAGE = "Call not cannot be cancelled without initiating a call";
        public static final String ERROR_CALL_MESSAGE = "Something went wrong while connecting.";
        public static final String ERROR_CALL_SESSION_MISMATCH_MESSAGE = "Call Session Mismatch. Please check the session ID";
        public static final String ERROR_INIT_NOT_CALLED_MESSAGE = "CometChat.init() not called. Please call the method preferably in the onCreate() method of the Application class before calling any other methods related to CometChat.";
        public static final String ERROR_UID_GUID_NOT_SPECIFIED_MESSAGE = "Both UID and GUID cannot be null. Please specify the UID or the GUID for which the messages are to be fetched.";
        public static final String ERROR_INTERNET_UNAVAILABLE_MESSAGE = "No Internet Connection. Please try again later.";
        public static final String ERROR_REQUEST_IN_PROGRESS_MESSAGE = "Request Already in Progress.";
        public static final String ERROR_FILTERS_MISSING_MESSAGE = "'Timestamp' or 'MessageId' or `updatedAfter` required to use the 'fetchNext()' method.";
        public static final String ERROR_BLANK_AUTHTOKEN_MESSAGE = "Auth Token cannot be empty. Please provide a valid auth token.";
        public static final String ERROR_EXTENSION_DISABLED_MESSAGE = "The extension is disabled. Please enabled the extension from CometChat Dashboard.";
        public static final String ERROR_INVALID_MESSAGEID_MESSAGE = "The message ID provided is invalid. Please provide a valid Message ID";
        public static final String ERROR_INVALID_MESSAGE_TYPE_MESSAGE = "Only TextMessage or CustomMessage can be edited. Please provide a valid message";
        public static final String ERROR_LIST_EMPTY_MESSAGE = "The list provided is empty. Please provide a valid list.";
        public static final String ERROR_UPDATESONLY_WITHOUT_UPDATEDAFTER_MESSAGE = "The `updatesOnly()` method cannot be used without the `setUpdatedAfter()` method";
        public static final String ERROR_JSON_MESSAGE = "Error while parsing JSON.";
        public static final String ERROR_LOGOUT_FAIL_MESSAGE = "Error while logging out";
        public static final String ERROR_EMPTY_APPID_MESSAGE = "AppID cannot be empty. Please specify a valid appID";
        public static final String ERROR_NON_POSITIVE_LIMIT_MESSSAGE = "The limit specified must be a positive number.";
        public static final String ERROR_REGION_MISSING_MESSAGE = "Region not specified. Please specify the region in the AppSettingsBuilder class using the `setRegion()` method";
        public static final String ERROR_APP_SETTING_NULL_MESSAGE = "The AppSettings cannot be null";
        public static final String ERROR_API_KEY_NOT_FOUND_MESSAGE = "ApiKey cannot be null or empty. Please provide a valid Api Key";
        public static final String ERROR_INVALID_SENDING_MESSAGE_TYPE_MESSAGE = "The Message type you have entered is invalid for following Action.";
        public static final String ERROR_MESSAGE_TEXT_EMPTY_MESSAGE = "Message text cannot be empty.";
        public static final String ERROR_FILE_OBJECT_INVALID_MESSAGE = "File object cannot be null or File path doesnt exist";
        public static final String ERROR_FILE_URL_EMPTY_MESSAGE = "Media Message URL is set as null or blank.";
        public static final String ERROR_EMPTY_CUSTOM_DATA_MESSAGE = "Custom data field cannot be null or empty.";
        public static final String ERROR_EMPTY_GROUP_NAME_MESSAGE = "Group Name cannot be null or empty.";
        public static final String ERROR_EMPTY_GROUP_TYPE_MESSAGE = "Group Type cannot be null or empty";
        public static final String ERROR_LOGIN_IN_PROGRESS_MESSAGE = "Login in progress. Please wait for the login request to finish";
        public static final String ERROR_INVALID_MESSAGE_MESSAGE = "Message cannot be null. Please pass a valid `BaseMessage` object";
        public static final String ERROR_INVALID_GROUP_MESSAGE = "Group cannot be null. Please pass a valid `Group` object";
        public static final String ERROR_INVALID_CALL_MESSAGE = "Call cannot be null. Please pass a valid `Call` object";
        public static final String ERROR_INVALID_CALL_TYPE_MESSAGE = "Invalid Call Type. Please provide a valid Call type.";
        public static final String ERROR_INVALID_RECEIVER_TYPE_MESSAGE = "Invalid Receiver Type Type. Please provide a valid Receiver type.";
        public static final String ERROR_INVALID_SESSION_ID_MESSAGE = "Invalid sessionId Type. Please provide a valid session id.";
        public static final String ERROR_ACTIVITY_NULL_MESSAGE = "Provided Activity cannot be null.";
        public static final String ERROR_VIEW_NULL_MESSAGE = "Provided RelativeLayout cannot be null";
        public static final String ERROR_INVALID_FCM_TOKEN_MESSAGE = "The FCM Token provided cannot be null or empty. Please provide a valid FCM Token";
        public static final String ERROR_INVALID_GROUP_TYPE_MESSAGE = "The group type provided cannot be null or empty";
        public static final String ERROR_CALL_IN_PROGRESS_MESSAGE = "Call In Progress. Please end the previous call to perform this operation.";
        public static final String ERROR_INCORRECT_INITIATOR_MESSAGE = "Cannot cancel call initiated by someone else. Please use status `rejected` instead";
        public static final String ERROR_INVALID_USER_NAME_MESSAGE = "Inavlid name provided for the user. Please provide a valid name";
        public static final String ERROR_INVALID_USER_MESSAGE = "User object cannot be null. Please provide a valid User object";
        public static final String ERROR_INVALID_GROUP_NAME_MESSAGE = "Group Name cannot be empty. Please provide a valid Group Name";
        public static final String ERROR_INVALID_TIMESTAMP_MESSAGE = "Timestamp has to be positive. Please provide a valid timestamp";
        public static final String ERROR_INVALID_CATEGORY_MESSAGE = "Invalid Category. Please provide a valid category";
        public static final String ERROR_EMPTY_ICON_MESSAGE = "Empty Icon. Please provide a valid icon";
        public static final String ERROR_EMPTY_DESCRIPTION_MESSAGE = "Empty Description. Please provide a valid description";
        public static final String ERROR_EMPTY_METADATA_MESSAGE = "Empty Metatdata. Please provide a valid metadata";
        public static final String ERROR_EMPTY_SCOPE_MESSAGE = "Invalid Scope. Please provide a valid scope";
        public static final String ERROR_INVALID_SCOPE_MESSAGE = "Invalid Scope. Please provide a valid scope";
        public static final String ERROR_INVALID_CONVERSATION_WITH_MESSAGE = "Invalid conversationWith. Please provide a valid value for conversationWith";
        public static final String ERROR_INVALID_CONVERSATION_TYPE_MESSAGE = "Invalid conversationType. Please provide a valid value for conversationWith";
        public static final String ERROR_INVALID_MEDIA_MESSAGE_MESSAGE = "Invalid Media Message.Please provide a valid File object or Attachment details.";
        public static final String ERROR_INVALID_ATTACHMENT_MESSAGE = "Attachment cannot be null. Please provide valid attachment details";
        public static final String ERROR_INVALID_FILE_NAME_MESSAGE = "Invalid File Name. Please provide a valid file name";
        public static final String ERROR_INVALID_FILE_EXTENSION_MESSAGE = "Invalid File Extension. Please provide a valid file extension";
        public static final String ERROR_INVALID_FILE_MIME_TYPE_MESSAGE = "Invalid File Mime Type. Please provide a valid file mime type";
        public static final String ERROR_INVALID_FILE_URL_MESSAGE = "Invalid File URL. Please provide a valid file URL";
        public static final String ERROR_CONVERSATION_NOT_FOUND_MESSAGE = "Conversation not found for conversationWith %s and conversationType %s";

        public Errors() {
        }
    }

    public static final class ResponseKeys {
        public static final String KEY_DATA = "data";
        public static final String KEY_ACTION = "action";
        public static final String KEY_MESSAGE = "message";
        public static final String KEY_ERROR = "error";
        public static final String KEY_ERROR_DETAILS = "details";
        public static final String KEY_ERROR_CODE = "code";
        public static final String KEY_ERROR_MESSAGE = "message";
        public static final String KEY_AUTH_TOKEN = "authToken";
        public static final String KEY_WS_CHANNEL = "wsChannel";
        public static final String KEY_IDENTITY = "identity";
        public static final String KEY_SERVICE = "service";
        public static final String KEY_ENTITIES = "entities";
        public static final String KEY_ENTITITY = "entity";
        public static final int CODE_REQUEST_OK = 200;
        public static final int CODE_BAD_REQUEST = 400;
        public static final String KEY_BLOCKED_UIDS = "blockedUids";
        public static final String SUCCESS = "success";
        public static final String KEY_RECEIPTS = "receipts";
        public static final String KEY_MESSAGE_ID = "messageId";
        public static final String KEY_RECIPIENT = "recipient";
        public static final String KEY_ENTITY_ID = "entityId";
        public static final String KEY_ENTITY_TYPE = "entityType";
        public static final String KEY_COUNT = "count";
        public static final String KEY_MY_RECEIPT = "myReceipt";
        public static final String KEY_STATUS_AVAILABLE = "available";
        public static final String KEY_SETTINGS = "settings";

        public ResponseKeys() {
        }
    }

    public static final class UserKeys {
        public static final String USER_KEY_UID = "uid";
        public static final String USER_KEY_NAME = "name";
        public static final String USER_KEY_AVATAR = "avatar";
        public static final String USER_KEY_LINK = "link";
        public static final String USER_KEY_ROLE = "role";
        public static final String USER_KEY_METADATA = "metadata";
        public static final String USER_KEY_CREDITS = "credits";
        public static final String USER_KEY_STATUS = "status";
        public static final String USER_KEY_STATUS_MESSAGE = "statusMessage";
        public static final String USER_KEY_LAST_ACTIVE_AT = "lastActiveAt";
        public static final String USER_KEY_HAS_BLOCKED_ME = "hasBlockedMe";
        public static final String USER_KEY_BLOCKED_BY_ME = "blockedByMe";

        public UserKeys() {
        }
    }

    public static final class Params {
        public static final String UID = "uid";
        public static final String APPID = "appid";
        public static final String APIKEY = "apikey";
        public static final String AUTHTOKEN = "authToken";
        public static final String PASSWORD = "password";
        public static final String LIMIT = "per_page";
        public static final String TOKEN = "cursor";
        public static final String PAGE = "page";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String CONTENT_TYPE_JSON_VALUE = "application/json";
        public static final String CONTENT_TYPE_JSON_MULTIPART = "multipart/form-data";
        public static final String KEY_SEARCH_KEYWORD = "searchKey";
        public static final String KEY_STATUS = "status";
        public static final String KEY_HIDE_BLOCKED_USERS = "hideBlockedUsers";
        public static final String KEY_DIRECTION = "direction";
        public static final String KEY_UNDELIVERED = "undelivered";
        public static final String KEY_UNREAD = "unread";
        public static final String KEY_COUNT = "count";
        public static final String KEY_HIDE_MESSAGES_FROM_BLOCKED_USERS = "hideMessagesFromBlockedUsers";
        public static final String KEY_UPDATED_AT = "updatedAt";
        public static final String KEY_ONLY_UPDATES = "onlyUpdates";
        public static final String KEY_ROLE = "role";
        public static final String KEY_HAS_JOINED = "hasJoined";
        public static final String KEY_FRIENDS_ONLY = "friendsOnly";
        public static final String KEY_CONVERSATION_WITH = "conversationWith";
        public static final String KEY_CONVERSATION_TYPE = "conversationType";
        public static final String KEY_HEADER_RESOURCE = "resource";
        public static final String KEY_HEADER_SDK_VERSION = "sdk";
        public static final String KEY_HIDE_REPLIES = "hideReplies";

        public Params() {
        }
    }
}

