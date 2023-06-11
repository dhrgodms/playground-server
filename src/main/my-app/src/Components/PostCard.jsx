import {
    Card,
    CardHeader,
    CardFooter,
    Image,
    Heading,
    Text,
    Button,
    Flex,
    Box,
} from '@chakra-ui/react';

import { ChatIcon, ExternalLinkIcon } from '@chakra-ui/icons';
import {useNavigate} from "react-router-dom";
import React from "react";

export const PostCard = ({ post }) => {
    const navigate = useNavigate();
    const btnRef = React.useRef();
    const  handleCardClick = () => {
        navigate(`/post/${post.id}`);
    };
    return (
        <Card maxW="md" style={{cursor:'pointer'}} onClick={handleCardClick} ref={btnRef}>
            <CardHeader>
                <Flex spacing="4">
                    <Flex flex="1" gap="4" alignItems="center" flexWrap="wrap">
                        <Box>
                            <Heading size="md" noOfLines="1">{post.contentTitle}</Heading>
                            <Text noOfLines="1">{post.content}</Text>
                        </Box>
                    </Flex>
                </Flex>
            </CardHeader>
            <Image
                objectFit="cover"
                src={post.thumbnail}
                alt="Chakra UI"
            />

            <CardFooter
                justify="space-between"
                flexWrap="wrap"
                sx={{
                    '& > button': {
                        minW: '136px',
                    },
                }}
            >
                <Button flex="1" variant="ghost" leftIcon={<ChatIcon />}>
                    Comment
                </Button>
                <Button flex="1" variant="ghost" leftIcon={<ExternalLinkIcon />}>
                    Share
                </Button>
            </CardFooter>
        </Card>
    );
};
